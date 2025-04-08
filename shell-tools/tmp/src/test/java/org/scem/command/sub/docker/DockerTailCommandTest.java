package org.scem.command.sub.docker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.scem.command.exception.ExecutionCommandException;

import static org.junit.jupiter.api.Assertions.*;

class DockerTailCommandTest {

    @Test
    @DisplayName("run should execute docker compose logs -f successfully")
    void testRun_shouldExecuteSuccessfully() {
        DockerTailCommand command = new DockerTailCommand() {
            @Override
            public void executeCommand(String... command) {
                assertNotNull(command);
                assertEquals(4, command.length);
                assertEquals("docker", command[0]);
                assertEquals("compose", command[1]);
                assertEquals("logs", command[2]);
                assertEquals("-f", command[3]);
            }
        };

        assertDoesNotThrow(command::run);
    }

    @Test
    @DisplayName("run should throw ExecutionCommandException when executeCommand fails")
    void testRun_shouldThrowExecutionCommandException() {
        DockerTailCommand command = new DockerTailCommand() {
            @Override
            public void executeCommand(String... command) {
                throw new RuntimeException("Simulated command failure");
            }
        };

        ExecutionCommandException exception = assertThrows(ExecutionCommandException.class, command::run);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Failed to execute DockerTailCommand"));
        assertNotNull(exception.getCause());
        assertEquals("Simulated command failure", exception.getCause().getMessage());
    }

    @Test
    @DisplayName("getLogger should return a non-null logger")
    void testGetLogger_shouldReturnLogger() {
        DockerTailCommand command = new DockerTailCommand();
        assertNotNull(command.getLogger());
    }
}
