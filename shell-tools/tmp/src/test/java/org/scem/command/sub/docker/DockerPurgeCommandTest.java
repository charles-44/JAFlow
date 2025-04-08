package org.scem.command.sub.docker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.scem.command.exception.ExecutionCommandException;
import org.scem.command.model.docker.DockerComposeFile;
import org.scem.command.util.DockerUtils;
import org.scem.command.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;

class DockerPurgeCommandTest {

    @Test
    @DisplayName("run should stop docker and purge volumes successfully")
    void testRun_shouldPurgeVolumesSuccessfully() {
        File mockDirectory = new File("TestProject");
        DockerComposeFile mockDockerComposeFile = new DockerComposeFile();
        mockDockerComposeFile.setVolumes(Collections.singletonMap("db-data", new Object()));

        try (
                MockedStatic<FileUtils> fileUtilsMockedStatic = Mockito.mockStatic(FileUtils.class);
                MockedStatic<DockerUtils> dockerUtilsMockedStatic = Mockito.mockStatic(DockerUtils.class);
                MockedStatic<DockerStopCommand> dockerStopCommandMockedStatic = Mockito.mockStatic(DockerStopCommand.class, CALLS_REAL_METHODS)
        ) {
            fileUtilsMockedStatic.when(FileUtils::getRootProjectDirectory).thenReturn(mockDirectory);
            dockerUtilsMockedStatic.when(() -> DockerUtils.getDockerCompose(mockDirectory)).thenReturn(mockDockerComposeFile);

            DockerPurgeCommand command = new DockerPurgeCommand() {
                @Override
                public void executeCommand(String... command) throws IOException {
                    assertTrue(command[0].startsWith("docker volume rm testproject_db-data"));
                }
            };

            assertDoesNotThrow(command::run);
        }
    }

    @Test
    @DisplayName("run should throw ExecutionCommandException when executeCommand fails")
    void testRun_shouldThrowWhenExecuteCommandFails() {
        File mockDirectory = new File("MyApp");
        DockerComposeFile mockDockerComposeFile = new DockerComposeFile();
        mockDockerComposeFile.setVolumes(Collections.singletonMap("volume1", new Object()));

        try (
                MockedStatic<FileUtils> fileUtilsMockedStatic = Mockito.mockStatic(FileUtils.class);
                MockedStatic<DockerUtils> dockerUtilsMockedStatic = Mockito.mockStatic(DockerUtils.class)
        ) {
            fileUtilsMockedStatic.when(FileUtils::getRootProjectDirectory).thenReturn(mockDirectory);
            dockerUtilsMockedStatic.when(() -> DockerUtils.getDockerCompose(mockDirectory)).thenReturn(mockDockerComposeFile);

            DockerPurgeCommand command = new DockerPurgeCommand() {
                @Override
                public void executeCommand(String... command) throws IOException {
                    throw new IOException("Docker error");
                }
            };

            ExecutionCommandException exception = assertThrows(ExecutionCommandException.class, command::run);
            assertTrue(exception.getCause().getMessage().contains("Unable to purge volume"));
        }
    }

    @Test
    @DisplayName("run should throw ExecutionCommandException on root failure")
    void testRun_shouldThrowWhenRootErrorOccurs() {
        try (MockedStatic<FileUtils> fileUtilsMockedStatic = Mockito.mockStatic(FileUtils.class)) {
            fileUtilsMockedStatic.when(FileUtils::getRootProjectDirectory).thenThrow(new RuntimeException("FS failure"));

            DockerPurgeCommand command = new DockerPurgeCommand();

            ExecutionCommandException exception = assertThrows(ExecutionCommandException.class, command::run);
            assertTrue(exception.getMessage().contains("Failed to execute DockerPurgeCommand"));
        }
    }

    @Test
    @DisplayName("getLogger should return non-null logger")
    void testGetLogger_shouldReturnLogger() {
        DockerPurgeCommand command = new DockerPurgeCommand();
        assertNotNull(command.getLogger());
    }
}
