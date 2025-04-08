package org.scem.command.sub.docker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.scem.command.exception.ExecutionCommandException;
import org.slf4j.Logger;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link DockerStartCommand} class.
 */
@ExtendWith(MockitoExtension.class)
class DockerStartCommandTest {

    @Spy
    private DockerStartCommand dockerStartCommand;

    // We use MockedConstruction to intercept the creation of DockerTailCommand
    // This allows us to verify its run() method is called without executing it.
    @Mock
    private DockerTailCommand mockDockerTailCommand;

    @Test
    @DisplayName("run() should successfully execute start command and tail command")
    void run_shouldExecuteSuccessfully() throws Exception {
        // Arrange
        // Prevent actual execution of the docker command
        doNothing().when(dockerStartCommand).executeCommand("docker", "compose", "up", "-d");

        // Mock the construction of DockerTailCommand to verify its run method is called
        try (MockedConstruction<DockerTailCommand> mockedTailConstruction = mockConstruction(
                DockerTailCommand.class,
                (mock, context) -> {
                    // Configure the mocked instance if needed, e.g., mock its run()
                    doNothing().when(mock).run();
                })) {

            // Act
            assertDoesNotThrow(() -> dockerStartCommand.run(),
                    "run() should not throw an exception during successful execution.");

            // Assert
            // Verify executeCommand was called correctly
            verify(dockerStartCommand, times(1)).executeCommand("docker", "compose", "up", "-d");
            verify(dockerStartCommand, times(1)).executeCommand(anyString(), anyString(), anyString(), anyString()); // General verification

            // Verify a DockerTailCommand was constructed and its run() method called
            assertEquals(1, mockedTailConstruction.constructed().size(), "A DockerTailCommand instance should have been constructed.");
            DockerTailCommand constructedTailCommand = mockedTailConstruction.constructed().get(0);
            assertNotNull(constructedTailCommand, "Constructed DockerTailCommand should not be null.");
            verify(constructedTailCommand, times(1)).run(); // Verify run() was called on the constructed instance
        }
    }

    @Test
    @DisplayName("run() should throw ExecutionCommandException when executeCommand fails")
    void run_shouldThrowExecutionCommandException_whenExecuteCommandFails() throws Exception {
        // Arrange
        IOException cause = new IOException("Docker compose failed");
        String expectedMessage = "Failed to execute DockerStartCommand";
        // Configure executeCommand to throw an exception
        doThrow(cause).when(dockerStartCommand).executeCommand("docker", "compose", "up", "-d");

        // Mock the construction - DockerTailCommand should NOT be constructed or run if executeCommand fails
        try (MockedConstruction<DockerTailCommand> mockedTailConstruction = mockConstruction(DockerTailCommand.class)) {

            // Act & Assert
            ExecutionCommandException thrown = assertThrows(ExecutionCommandException.class, () -> {
                dockerStartCommand.run();
            }, "run() should throw ExecutionCommandException when executeCommand fails.");

            // Assert exception details
            assertNotNull(thrown, "Thrown exception should not be null.");
            assertEquals(expectedMessage, thrown.getMessage(), "Exception message should match.");
            assertNotNull(thrown.getCause(), "Exception cause should not be null.");
            assertSame(cause, thrown.getCause(), "Exception cause should be the original IOException.");
            assertTrue(thrown.getCause() instanceof IOException, "Cause should be an instance of IOException.");

            // Verify executeCommand was called
            verify(dockerStartCommand, times(1)).executeCommand("docker", "compose", "up", "-d");

            // Verify DockerTailCommand was NOT constructed or run
            assertEquals(0, mockedTailConstruction.constructed().size(), "DockerTailCommand should not have been constructed.");
        }
    }

    @Test
    @DisplayName("run() should throw ExecutionCommandException when DockerTailCommand run fails")
    void run_shouldThrowExecutionCommandException_whenTailCommandFails() throws Exception {
        // Arrange
        RuntimeException tailException = new RuntimeException("Tail command simulation failure");
        String expectedMessage = "Failed to execute DockerStartCommand";
        // Allow executeCommand to succeed
        doNothing().when(dockerStartCommand).executeCommand("docker", "compose", "up", "-d");

        // Mock the construction of DockerTailCommand to make its run() method throw an exception
        try (MockedConstruction<DockerTailCommand> mockedTailConstruction = mockConstruction(
                DockerTailCommand.class,
                (mock, context) -> {
                    // Configure the mocked instance's run() method to throw
                    doThrow(tailException).when(mock).run();
                })) {

            // Act & Assert
            ExecutionCommandException thrown = assertThrows(ExecutionCommandException.class, () -> {
                dockerStartCommand.run();
            }, "run() should throw ExecutionCommandException when DockerTailCommand's run() fails.");

            // Assert exception details
            assertNotNull(thrown, "Thrown exception should not be null.");
            assertEquals(expectedMessage, thrown.getMessage(), "Exception message should match.");
            assertNotNull(thrown.getCause(), "Exception cause should not be null.");
            assertSame(tailException, thrown.getCause(), "Exception cause should be the RuntimeException from tail command.");
            assertTrue(thrown.getCause() instanceof RuntimeException, "Cause should be an instance of RuntimeException.");

            // Verify executeCommand was called
            verify(dockerStartCommand, times(1)).executeCommand("docker", "compose", "up", "-d");

            // Verify DockerTailCommand was constructed and its run() was called (which threw the exception)
            assertEquals(1, mockedTailConstruction.constructed().size(), "A DockerTailCommand instance should have been constructed.");
            DockerTailCommand constructedTailCommand = mockedTailConstruction.constructed().get(0);
            assertNotNull(constructedTailCommand, "Constructed DockerTailCommand should not be null.");
            verify(constructedTailCommand, times(1)).run(); // Verify run() was called
        }
    }


    @Test
    @DisplayName("getLogger() should return a non-null Logger instance")
    void getLogger_shouldReturnNonNullLogger() {
        // Act
        Logger logger = dockerStartCommand.getLogger();

        // Assert
        assertNotNull(logger, "getLogger() should return a non-null Logger instance.");
    }

    @Test
    @DisplayName("getLogger() should return the same Logger instance on multiple calls")
    void getLogger_shouldReturnSameInstance() {
        // Act
        Logger logger1 = dockerStartCommand.getLogger();
        Logger logger2 = dockerStartCommand.getLogger();

        // Assert
        assertNotNull(logger1, "First logger instance should not be null.");
        assertNotNull(logger2, "Second logger instance should not be null.");
        assertSame(logger1, logger2, "getLogger() should return the same static Logger instance across calls.");
    }

}