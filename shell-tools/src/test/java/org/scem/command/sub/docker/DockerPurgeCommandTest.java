package org.scem.command.sub.docker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.scem.command.exception.ExecutionCommandException;
import org.scem.command.model.docker.DockerComposeFile;
import org.scem.command.util.DockerUtils;
import org.scem.command.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DockerPurgeCommandTest {

    private DockerPurgeCommand command;

    @BeforeEach
    void setUp() {
        command = Mockito.spy(new DockerPurgeCommand());
    }

    @Test
    void testGetLoggerShouldReturnNonNullLogger() {
        assertNotNull(command.getLogger());
    }

    @Test
    void testRunShouldExecuteVolumePurgeSuccessfully() {
        try (
                MockedStatic<FileUtils> fileUtilsMock = mockStatic(FileUtils.class);
                MockedStatic<DockerUtils> dockerUtilsMock = mockStatic(DockerUtils.class);
                MockedConstruction<DockerStopCommand> stopCommandMock = mockConstruction(DockerStopCommand.class)
        ) {
            File mockDir = mock(File.class);
            when(mockDir.getName()).thenReturn("MyProject");
            fileUtilsMock.when(FileUtils::getRootProjectDirectory).thenReturn(mockDir);

            Map<String, Object> volumes = new LinkedHashMap<>();
            volumes.put("db_data", new Object());
            volumes.put("cache_data", new Object());

            DockerComposeFile mockDockerCompose = mock(DockerComposeFile.class);
            when(mockDockerCompose.getVolumes()).thenReturn(volumes);
            dockerUtilsMock.when(() -> DockerUtils.getDockerCompose(mockDir)).thenReturn(mockDockerCompose);

            doNothing().when(command).executeCommand(anyString());

            assertDoesNotThrow(() -> command.run());

            verify(command, times(1)).executeCommand("docker volume rm myproject_db_data");
            verify(command, times(1)).executeCommand("docker volume rm myproject_cache_data");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testRunShouldThrowExecutionCommandExceptionOnDockerUtilsError() {
        try (
                MockedStatic<FileUtils> fileUtilsMock = mockStatic(FileUtils.class);
                MockedStatic<DockerUtils> dockerUtilsMock = mockStatic(DockerUtils.class)
        ) {
            File mockDir = mock(File.class);
            when(mockDir.getName()).thenReturn("MyProject");
            fileUtilsMock.when(FileUtils::getRootProjectDirectory).thenReturn(mockDir);

            dockerUtilsMock.when(() -> DockerUtils.getDockerCompose(mockDir))
                    .thenThrow(new RuntimeException("Simulated failure"));

            ExecutionCommandException exception = assertThrows(ExecutionCommandException.class, command::run);
            assertTrue(exception.getMessage().contains("Failed to execute DockerPurgeCommand"));
            assertNotNull(exception.getCause());
            assertEquals("Simulated failure", exception.getCause().getMessage());
        }
    }

    @Test
    void testRunShouldThrowExecutionCommandExceptionWhenVolumeRemoveFails() throws IOException {
        try (
                MockedStatic<FileUtils> fileUtilsMock = mockStatic(FileUtils.class);
                MockedStatic<DockerUtils> dockerUtilsMock = mockStatic(DockerUtils.class);
                MockedConstruction<DockerStopCommand> stopCommandMock = mockConstruction(DockerStopCommand.class)
        ) {
            File mockDir = mock(File.class);
            when(mockDir.getName()).thenReturn("MyProject");
            fileUtilsMock.when(FileUtils::getRootProjectDirectory).thenReturn(mockDir);

            Map<String, Object> volumes = Map.of("db_data", new Object());

            DockerComposeFile mockDockerCompose = mock(DockerComposeFile.class);
            when(mockDockerCompose.getVolumes()).thenReturn(volumes);
            dockerUtilsMock.when(() -> DockerUtils.getDockerCompose(mockDir)).thenReturn(mockDockerCompose);

            doThrow(new IOException("Docker volume remove failed"))
                    .when(command).executeCommand("docker volume rm myproject_db_data");

            ExecutionCommandException exception = assertThrows(ExecutionCommandException.class, command::run);
            assertTrue(exception.getMessage().contains("Failed to execute DockerPurgeCommand"));
            assertNotNull(exception.getCause());
            assertEquals("Unable to purge volume", exception.getCause().getMessage());
        }
    }

}
