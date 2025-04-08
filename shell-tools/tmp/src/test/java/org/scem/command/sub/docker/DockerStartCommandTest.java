package org.scem.command.sub.docker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.scem.command.exception.ExecutionCommandException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

class DockerStartCommandTest {

    @Test
    @DisplayName("run should execute docker compose up and tail logs without exception")
    void testRun_shouldExecuteSuccessfully() {
        try (MockedConstruction<DockerTailCommand> mocked = Mockito.mockConstruction(DockerTailCommand.class,
                (mock, context) -> doNothing().when(mock).run())) {

            DockerStartCommand command = new DockerStartCommand() {
                @Override
                public void executeCommand(String... command) {
                    assertEquals(4, command.length);
                    assertEquals("docker", command[0]);
                    assertEquals("compose", command[1]);
                    assertEquals("up", command[2].split(" ")[0]); // check up -d
                }
            };

            assertDoesNotThrow(command::run);
            assertEquals(1, mocked.constructed().size());
        }
    }

    @Test
    @DisplayName("run should throw ExecutionCommandException when executeCommand fails")
    void testRun_shouldThrowWhenExecuteCommandFails() {
        DockerStartCommand command = new DockerStartCommand() {
            @Override
            public void executeCommand(String... command) {
                throw new RuntimeException("Docker execution failed");
            }
        };

        ExecutionCommandException exception = assertThrows(ExecutionCommandException.class, command::run);
        assertTrue(exception.getMessage().contains("Failed to execute DockerStartCommand"));
        assertTrue(exception.getCause().getMessage().contains("Docker execution failed"));
    }

    @Test
    @DisplayName("run should throw ExecutionCommandException when DockerTailCommand fails")
    void testRun_shouldThrowWhenDockerTailFails() {
        try (MockedConstruction<DockerTailCommand> mocked = Mockito.mockConstruction(DockerTailCommand.class,
                (mock, context) -> doThrow(new RuntimeException("Tail error")).when(mock).run())) {

            DockerStartCommand command = new DockerStartCommand() {
                @Override
                public void executeCommand(String... command) {
                    // No-op
                }
            };

            ExecutionCommandException exception = assertThrows(ExecutionCommandException.class, command::run);
            assertTrue(exception.getMessage().contains("Failed to execute DockerStartCommand"));
            assertTrue(exception.getCause().getMessage().contains("Tail error"));
        }
    }

    @Test
    @DisplayName("getLogger should return non-null logger")
    void testGetLogger_shouldReturnLogger() {
        DockerStartCommand command = new DockerStartCommand();
        assertNotNull(command.getLogger());
    }
}
