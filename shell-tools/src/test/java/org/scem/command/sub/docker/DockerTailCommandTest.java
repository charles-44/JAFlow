package org.scem.command.sub.docker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.scem.command.exception.ExecutionCommandException;
import org.slf4j.Logger;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link DockerTailCommand} class.
 */
@ExtendWith(MockitoExtension.class)
class DockerTailCommandTest {

    @Spy
    private DockerTailCommand dockerTailCommand;


    @Test
    @DisplayName("run() should successfully call executeCommand with 'docker compose logs -f'")
    void run_shouldCallExecuteCommandSuccessfully() throws Exception {
        // Arrange
        // Configure the spy to do nothing when executeCommand is called.
        doNothing().when(dockerTailCommand).executeCommand("docker", "compose", "logs", "-f");

        // Act
        assertDoesNotThrow(() -> dockerTailCommand.run(),
                "run() should not throw an exception when executeCommand succeeds.");

        // Assert
        // Verify that executeCommand was called exactly once with the expected arguments.
        verify(dockerTailCommand, times(1)).executeCommand("docker", "compose", "logs", "-f");
        verify(dockerTailCommand, times(1)).executeCommand(anyString(), anyString(), anyString(), anyString()); // General verification
    }

    @Test
    @DisplayName("run() should throw ExecutionCommandException when executeCommand throws IOException")
    void run_shouldThrowExecutionCommandException_whenExecuteCommandThrowsIOException() throws Exception {
        // Arrange
        String expectedErrorMessage = "Failed to execute DockerTailCommand";
        IOException ioException = new IOException("Simulated Docker logs error");

        // Configure the spy to throw an IOException when executeCommand is called.
        doThrow(ioException).when(dockerTailCommand).executeCommand("docker", "compose", "logs", "-f");

        // Act & Assert
        ExecutionCommandException thrownException = assertThrows(ExecutionCommandException.class, () -> {
            dockerTailCommand.run();
        }, "run() should throw ExecutionCommandException when executeCommand throws IOException.");

        // Assertions on the thrown exception
        assertNotNull(thrownException, "Thrown exception should not be null.");
        assertEquals(expectedErrorMessage, thrownException.getMessage(), "Exception message should match.");
        assertNotNull(thrownException.getCause(), "Exception cause should not be null.");
        assertSame(ioException, thrownException.getCause(), "Exception cause should be the original IOException.");
        assertTrue(thrownException.getCause() instanceof IOException, "Cause should be an instance of IOException.");

        // Verify that executeCommand was still called exactly once.
        verify(dockerTailCommand, times(1)).executeCommand("docker", "compose", "logs", "-f");
    }

    @Test
    @DisplayName("run() should throw ExecutionCommandException when executeCommand throws generic Exception")
    void run_shouldThrowExecutionCommandException_whenExecuteCommandThrowsGenericException() throws Exception {
        // Arrange
        String expectedErrorMessage = "Failed to execute DockerTailCommand";
        RuntimeException runtimeException = new RuntimeException("Simulated generic error during command execution");

        // Configure the spy to throw a RuntimeException when executeCommand is called.
        doThrow(runtimeException).when(dockerTailCommand).executeCommand("docker", "compose", "logs", "-f");

        // Act & Assert
        ExecutionCommandException thrownException = assertThrows(ExecutionCommandException.class, () -> {
            dockerTailCommand.run();
        }, "run() should throw ExecutionCommandException when executeCommand throws a generic Exception.");

        // Assertions on the thrown exception
        assertNotNull(thrownException, "Thrown exception should not be null.");
        assertEquals(expectedErrorMessage, thrownException.getMessage(), "Exception message should match.");
        assertNotNull(thrownException.getCause(), "Exception cause should not be null.");
        assertSame(runtimeException, thrownException.getCause(), "Exception cause should be the original RuntimeException.");
        assertTrue(thrownException.getCause() instanceof RuntimeException, "Cause should be an instance of RuntimeException.");

        // Verify that executeCommand was still called exactly once.
        verify(dockerTailCommand, times(1)).executeCommand("docker", "compose", "logs", "-f");
    }


    @Test
    @DisplayName("getLogger() should return a non-null Logger instance")
    void getLogger_shouldReturnNonNullLogger() {
        // Act
        Logger logger = dockerTailCommand.getLogger();

        // Assert
        assertNotNull(logger, "getLogger() should return a non-null Logger instance.");
    }

    @Test
    @DisplayName("getLogger() should return the same Logger instance on multiple calls")
    void getLogger_shouldReturnSameInstance() {
        // Act
        Logger logger1 = dockerTailCommand.getLogger();
        Logger logger2 = dockerTailCommand.getLogger();

        // Assert
        assertNotNull(logger1, "First logger instance should not be null.");
        assertNotNull(logger2, "Second logger instance should not be null.");
        assertSame(logger1, logger2, "getLogger() should return the same static Logger instance across calls.");
    }

}