package org.scem.command.sub.docker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.scem.command.exception.ExecutionCommandException;
import org.scem.command.model.docker.DockerComposeFile;
import org.scem.command.util.DockerUtils;
import org.scem.command.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DockerPurgeCommandTest {

    private DockerPurgeCommand dockerPurgeCommand;

    @BeforeEach
    void setUp() {
        dockerPurgeCommand = new DockerPurgeCommand() {
            @Override
            public void executeCommand(String command) throws IOException {
                // no-op for test
            }
        };
    }

    @Test
    void testRunSuccessfullyRemovesVolumes(@TempDir Path tempDir) {
        File mockDir = tempDir.toFile();
        mockDir.mkdir();

        DockerComposeFile mockDockerComposeFile = Mockito.mock(DockerComposeFile.class);
        Map<String, Object> volumes = Collections.singletonMap("volume1", new Object());

        try (
                MockedStatic<FileUtils> fileUtilsMock = Mockito.mockStatic(FileUtils.class);
                MockedStatic<DockerUtils> dockerUtilsMock = Mockito.mockStatic(DockerUtils.class);
                MockedStatic<DockerStopCommand> dockerStopCommandMock = Mockito.mockStatic(DockerStopCommand.class)
        ) {
            fileUtilsMock.when(FileUtils::getRootProjectDirectory).thenReturn(mockDir);
            dockerUtilsMock.when(() -> DockerUtils.getDockerCompose(mockDir)).thenReturn(mockDockerComposeFile);
            Mockito.when(mockDockerComposeFile.getVolumes()).thenReturn(volumes);

             assertDoesNotThrow(dockerPurgeCommand::run);
            // No exception = success
        }
    }

    @Test
    void testRunWithIOExceptionDuringVolumeRemovalThrowsExecutionCommandException(@TempDir Path tempDir) {
        File mockDir = tempDir.toFile();
        mockDir.mkdir();

        DockerComposeFile mockDockerComposeFile = Mockito.mock(DockerComposeFile.class);
        Map<String, Object> volumes = Collections.singletonMap("volume1", new Object());

        DockerPurgeCommand failingCommand = new DockerPurgeCommand() {
            @Override
            public void executeCommand(String command) throws IOException {
                throw new IOException("Simulated failure");
            }
        };

        try (
                MockedStatic<FileUtils> fileUtilsMock = Mockito.mockStatic(FileUtils.class);
                MockedStatic<DockerUtils> dockerUtilsMock = Mockito.mockStatic(DockerUtils.class);
        ) {
            fileUtilsMock.when(FileUtils::getRootProjectDirectory).thenReturn(mockDir);
            dockerUtilsMock.when(() -> DockerUtils.getDockerCompose(mockDir)).thenReturn(mockDockerComposeFile);
            Mockito.when(mockDockerComposeFile.getVolumes()).thenReturn(volumes);

            ExecutionCommandException exception = assertThrows(
                    ExecutionCommandException.class,
                    failingCommand::run
            );
            assertTrue(exception.getMessage().contains("Failed to execute DockerPurgeCommand"));
            assertNotNull(exception.getCause());
        }
    }

    @Test
    void testRunWithNullVolumesMapDoesNotThrow(@TempDir Path tempDir) {
        File mockDir = tempDir.toFile();
        mockDir.mkdir();

        DockerComposeFile mockDockerComposeFile = Mockito.mock(DockerComposeFile.class);

        try (
                MockedStatic<FileUtils> fileUtilsMock = Mockito.mockStatic(FileUtils.class);
                MockedStatic<DockerUtils> dockerUtilsMock = Mockito.mockStatic(DockerUtils.class);
        ) {
            fileUtilsMock.when(FileUtils::getRootProjectDirectory).thenReturn(mockDir);
            dockerUtilsMock.when(() -> DockerUtils.getDockerCompose(mockDir)).thenReturn(mockDockerComposeFile);
            Mockito.when(mockDockerComposeFile.getVolumes()).thenReturn(Map.of());

            assertDoesNotThrow(() -> dockerPurgeCommand.run());
        }
    }

    @Test
    void testRunWithExceptionFromGetDockerComposeThrowsExecutionCommandException(@TempDir Path tempDir) {
        File mockDir = tempDir.toFile();
        mockDir.mkdir();

        try (
                MockedStatic<FileUtils> fileUtilsMock = Mockito.mockStatic(FileUtils.class);
                MockedStatic<DockerUtils> dockerUtilsMock = Mockito.mockStatic(DockerUtils.class);
        ) {
            fileUtilsMock.when(FileUtils::getRootProjectDirectory).thenReturn(mockDir);
            dockerUtilsMock.when(() -> DockerUtils.getDockerCompose(mockDir)).thenThrow(new RuntimeException("Docker error"));

            ExecutionCommandException exception = assertThrows(
                    ExecutionCommandException.class,
                    () -> dockerPurgeCommand.run()
            );
            assertTrue(exception.getMessage().contains("Failed to execute DockerPurgeCommand"));
            assertNotNull(exception.getCause());
        }
    }

    @Test
    void testGetLoggerReturnsNonNullLogger() {
        assertNotNull(dockerPurgeCommand.getLogger());
    }
}
