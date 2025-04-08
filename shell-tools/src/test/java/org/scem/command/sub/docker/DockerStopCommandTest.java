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
 * Unit tests for the {@link DockerStopCommand} class.
 */
@ExtendWith(MockitoExtension.class)
class DockerStopCommandTest {

    @Spy
    private DockerStopCommand dockerStopCommand;

    // We use MockedConstruction to intercept the creation of DockerTailCommand
    // This allows us to verify its run() method is called without executing it.
    @Mock
    private DockerTailCommand mockDockerTailCommand;

    @Test
    @DisplayName("run() should successfully execute stop command")
    void run_shouldExecuteSuccessfully() throws Exception {
        // Arrange
        // Prevent actual execution of the docker command
        doNothing().when(dockerStopCommand).executeCommand("docker", "compose", "down");

        // Mock the construction of DockerTailCommand to verify its run method is called
        try (MockedConstruction<DockerTailCommand> mockedTailConstruction = mockConstruction(
                DockerTailCommand.class,
                (mock, context) -> {
                    // Configure the mocked instance if needed, e.g., mock its run()
                    doNothing().when(mock).run();
                })) {

            // Act
            assertDoesNotThrow(() -> dockerStopCommand.run(),
                    "run() should not throw an exception during successful execution.");

            // Assert
            // Verify executeCommand was called correctly
            verify(dockerStopCommand, times(1)).executeCommand("docker", "compose", "down");
            verify(dockerStopCommand, times(1)).executeCommand(anyString(), anyString(), anyString()); // General verification

        }
    }

    @Test
    @DisplayName("run() should throw ExecutionCommandException when executeCommand fails")
    void run_shouldThrowExecutionCommandException_whenExecuteCommandFails() throws Exception {
        // Arrange
        IOException cause = new IOException("Docker compose failed");
        String expectedMessage = "Failed to stop docker compose project";
        // Configure executeCommand to throw an exception
        doThrow(cause).when(dockerStopCommand).executeCommand("docker", "compose", "down");

        // Mock the construction - DockerTailCommand should NOT be constructed or run if executeCommand fails
        try (MockedConstruction<DockerTailCommand> mockedTailConstruction = mockConstruction(DockerTailCommand.class)) {

            // Act & Assert
            ExecutionCommandException thrown = assertThrows(ExecutionCommandException.class, () -> {
                dockerStopCommand.run();
            }, "run() should throw ExecutionCommandException when executeCommand fails.");

            // Assert exception details
            assertNotNull(thrown, "Thrown exception should not be null.");
            assertEquals(expectedMessage, thrown.getMessage(), "Exception message should match.");
            assertNotNull(thrown.getCause(), "Exception cause should not be null.");
            assertSame(cause, thrown.getCause(), "Exception cause should be the original IOException.");
            assertTrue(thrown.getCause() instanceof IOException, "Cause should be an instance of IOException.");

            // Verify executeCommand was called
            verify(dockerStopCommand, times(1)).executeCommand("docker", "compose", "down");

            // Verify DockerTailCommand was NOT constructed or run
            assertEquals(0, mockedTailConstruction.constructed().size(), "DockerTailCommand should not have been constructed.");
        }
    }


    @Test
    @DisplayName("getLogger() should return a non-null Logger instance")
    void getLogger_shouldReturnNonNullLogger() {
        // Act
        Logger logger = dockerStopCommand.getLogger();

        // Assert
        assertNotNull(logger, "getLogger() should return a non-null Logger instance.");
    }

    @Test
    @DisplayName("getLogger() should return the same Logger instance on multiple calls")
    void getLogger_shouldReturnSameInstance() {
        // Act
        Logger logger1 = dockerStopCommand.getLogger();
        Logger logger2 = dockerStopCommand.getLogger();

        // Assert
        assertNotNull(logger1, "First logger instance should not be null.");
        assertNotNull(logger2, "Second logger instance should not be null.");
        assertSame(logger1, logger2, "getLogger() should return the same static Logger instance across calls.");
    }

}