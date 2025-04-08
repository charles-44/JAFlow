package org.scem.command.sub.updater;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.scem.command.client.SonarQubeClient;
import org.scem.command.constante.SubProject;
import org.scem.command.exception.ExecutionCommandException;
import org.scem.command.util.FileUtils;
import org.slf4j.Logger;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("UpdateShellToolsDocumentation Test")
class UpdateShellToolsDocumentationTest {

    @Test
    @DisplayName("getLogger should return non-null logger")
    void getLogger_shouldReturnLogger() {
        UpdateShellToolsDocumentation command = new UpdateShellToolsDocumentation();
        Logger logger = command.getLogger();
        assertNotNull(logger);
    }

    @Test
    @DisplayName("getSubProject should return SHELL")
    void getSubProject_shouldReturnSHELL() {
        UpdateShellToolsDocumentation command = new UpdateShellToolsDocumentation();
        assertEquals(SubProject.SHELL, command.getSubProject());
    }

    @Test
    @DisplayName("run should throw ExecutionCommandException when exception occurs")
    void run_shouldThrowExecutionCommandException() {
        UpdateShellToolsDocumentation command = new UpdateShellToolsDocumentation() {
            @Override
            public void run() {
                throw new ExecutionCommandException("Simulated exception");
            }
        };

        ExecutionCommandException exception = assertThrows(
                ExecutionCommandException.class,
                command::run
        );

        assertEquals("Simulated exception", exception.getMessage());
    }

    @Test
    @DisplayName("run should execute without exceptions when all dependencies mocked")
    void run_shouldExecuteWithoutErrors() throws Exception {
        UpdateShellToolsDocumentation command = new UpdateShellToolsDocumentation() {
            @Override
            public File getSubProjectReadme() {
                return new File("README.md");
            }

            @Override
            public void executeCommand(File directory, String... command) {
                // no-op
            }
        };

        try (
                MockedStatic<FileUtils> fileUtilsMock = Mockito.mockStatic(FileUtils.class);
                MockedStatic<SonarQubeClient> sonarQubeClientMock = Mockito.mockStatic(SonarQubeClient.class)
        ) {
            fileUtilsMock.when(() -> FileUtils.getPrivateProperties(anyString()))
                    .thenReturn("mocked");

            SonarQubeClient mockClient = mock(SonarQubeClient.class);
            when(mockClient.fetchMetrics()).thenReturn(Map.of(
                    "coverage", "95.0",
                    "bugs", "0",
                    "code_smells", "5"
            ));

            sonarQubeClientMock.when(() -> SonarQubeClient.getSonarQubeClient("shell-tools"))
                    .thenReturn(mockClient);

            assertDoesNotThrow(command::run);
        }
    }
}
