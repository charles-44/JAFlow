package org.scem.command.sub.updater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.scem.command.HelpCommands;
import org.scem.command.client.SonarQubeClient;
import org.scem.command.constante.SubProject;
import org.scem.command.exception.ExecutionCommandException;
import org.scem.command.util.FileUtils;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateShellToolsDocumentationTest {

    private UpdateShellToolsDocumentation command;

    @BeforeEach
    void setUp() {
        command = spy(new UpdateShellToolsDocumentation());
    }

    @Test
    void testGetLoggerShouldReturnNonNullLogger() {
        Logger logger = command.getLogger();
        assertNotNull(logger);
    }

    @Test
    void testGetSubProjectShouldReturnShell() {
        assertEquals(SubProject.SHELL, command.getSubProject());
    }

    @Test
    void testRunSuccessfullyUpdatesDocumentation() throws IOException {
        try (
                MockedStatic<FileUtils> fileUtilsMock = Mockito.mockStatic(FileUtils.class);
                MockedStatic<HelpCommands> helpCommandsMock = Mockito.mockStatic(HelpCommands.class);
                MockedStatic<SonarQubeClient> sonarClientStatic = Mockito.mockStatic(SonarQubeClient.class)
        ) {
            File mockFile = mock(File.class);
            File parentFile = mock(File.class);
            when(mockFile.getParentFile()).thenReturn(parentFile);
            doReturn(mockFile).when(command).getSubProjectReadme();

            fileUtilsMock.when(() -> FileUtils.getPrivateProperties("sonar.project.shell.token"))
                    .thenReturn("dummy-token");
            fileUtilsMock.when(() -> FileUtils.getPrivateProperties("maven.bin.path"))
                    .thenReturn("/usr/bin/mvn");
            fileUtilsMock.when(() -> FileUtils.getPrivateProperties("sonar.host.url"))
                    .thenReturn("http://localhost:9000");

            helpCommandsMock.when(() -> HelpCommands.getHelpCommands("\n```\n"))
                    .thenReturn("mock-help-content");

            SonarQubeClient sonarClient = mock(SonarQubeClient.class);
            Map<String, String> mockMetrics = new HashMap<>();
            mockMetrics.put("coverage", "95%");
            mockMetrics.put("bugs", "0");
            when(sonarClient.fetchMetrics()).thenReturn(mockMetrics);

            sonarClientStatic.when(() -> SonarQubeClient.getSonarQubeClient("shell-tools"))
                    .thenReturn(sonarClient);

            doNothing().when(command).executeCommand(any(), any(), any(), any(), any(), any(), any(), any(), any());
            doReturn(mockFile).when(command).replaceInReadme(any(), any());

            assertDoesNotThrow(() -> command.run());

            verify(command, times(1)).replaceInReadme(eq("AUTO_GENERATED_COMMAND"), eq("mock-help-content"));
            verify(command, times(1)).replaceInReadme(eq("AUTO_GENERATED_SONARQUBE_REPORT"), contains("coverage"));
        }
    }

    @Test
    void testRunThrowsExecutionCommandExceptionWhenExceptionOccurs() throws IOException {
        doThrow(new RuntimeException("Mock failure")).when(command).getSubProjectReadme();

        ExecutionCommandException thrown = assertThrows(ExecutionCommandException.class, new Executable() {
            @Override
            public void execute() {
                command.run();
            }
        });

        assertTrue(thrown.getMessage().contains("Failed to execute UpdateShellToolsDocumentation"));
        assertNotNull(thrown.getCause());
        assertEquals("Mock failure", thrown.getCause().getMessage());
    }

}
