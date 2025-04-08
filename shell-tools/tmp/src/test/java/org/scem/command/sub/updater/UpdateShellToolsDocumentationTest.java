package org.scem.command.sub.updater;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.reflections.Reflections;
import org.scem.command.constante.SubProject;
import org.scem.command.exception.ExecutionCommandException;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

class UpdateShellToolsDocumentationTest {

    @Test
    @DisplayName("run should throw ExecutionCommandException when reflection fails")
    void testRun_shouldThrowExceptionOnReflectionFailure() {
        try (
                MockedConstruction<Reflections> reflectionsMock = mockConstruction(Reflections.class,
                        (mock, context) -> when(mock.getTypesAnnotatedWith(CommandLine.Command.class))
                                .thenThrow(new RuntimeException("Reflection error")))
        ) {
            UpdateShellToolsDocumentation command = new UpdateShellToolsDocumentation();
            ExecutionCommandException exception = assertThrows(ExecutionCommandException.class, command::run);
            assertTrue(exception.getMessage().contains("Failed to execute UpdateShellToolsDocumentation"));
        }
    }

    @Test
    @DisplayName("getLogger should return a non-null logger")
    void testGetLogger_shouldReturnLogger() {
        UpdateShellToolsDocumentation command = new UpdateShellToolsDocumentation();
        assertNotNull(command.getLogger());
    }

    @Test
    @DisplayName("getSubProject should return SHELL")
    void testGetSubProject_shouldReturnShell() {
        UpdateShellToolsDocumentation command = new UpdateShellToolsDocumentation();
        assertEquals(SubProject.SHELL, command.getSubProject());
    }

    // Dummy command class for testing
    @CommandLine.Command(name = "dummy")
    public static class DummyCommand {
        public DummyCommand() {}
    }
}
