package org.scem.command.sub.docker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.scem.command.exception.ExecutionCommandException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DockerStopCommandTest {

    @Test
    @DisplayName("run should execute docker compose down successfully")
    void testRun_shouldExecuteSuccessfully() {
        DockerStopCommand command = new DockerStopCommand() {
            @Override
            public void executeCommand(String... command) throws IOException {
                assertNotNull(command);
                assertEquals(3, command.length);
                assertEquals("docker", command[0]);
                assertEquals("compose", command[1]);
                assertEquals("down", command[2]);
            }
        };

        assertDoesNotThrow(command::run);
    }

    @Test
    @DisplayName("run should throw ExecutionCommandException when executeCommand fails")
    void testRun_shouldThrowExecutionCommandException() {
        DockerStopCommand command = new DockerStopCommand() {
            @Override
            public void executeCommand(String... command) throws IOException {
                throw new IOException("Simulated failure");
            }
        };

        ExecutionCommandException exception = assertThrows(ExecutionCommandException.class, command::run);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Failed to stop docker compose project"));
        assertNotNull(exception.getCause());
        assertEquals("Simulated failure", exception.getCause().getMessage());
    }

    @Test
    @DisplayName("getLogger should return non-null logger")
    void testGetLogger_shouldReturnLogger() {
        DockerStopCommand command = new DockerStopCommand();
        assertNotNull(command.getLogger());
    }
}
