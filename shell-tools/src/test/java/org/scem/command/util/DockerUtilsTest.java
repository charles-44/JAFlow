package org.scem.command.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.scem.command.exception.DockerComposeFileAccessingException;
import org.scem.command.model.docker.DockerComposeFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DockerUtilsTest {

    @Test
    @DisplayName("getDockerCompose should parse valid docker-compose.yml successfully")
    void testGetDockerCompose_shouldReturnParsedObject() throws IOException {
        Path tempDir = java.nio.file.Files.createTempDirectory("docker-test");
        File dockerComposeFile = new File(tempDir.toFile(), "docker-compose.yml");

        String content = "version: '3.8'\nservices:\n  app:\n    image: nginx";
        try (FileOutputStream fos = new FileOutputStream(dockerComposeFile)) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
        }

        DockerComposeFile result = DockerUtils.getDockerCompose(tempDir.toFile());

        assertNotNull(result);
        assertEquals("3.8", result.getVersion());
        assertNotNull(result.getServices());
        assertTrue(result.getServices().containsKey("app"));
    }

    @Test
    @DisplayName("getDockerCompose should throw exception when file is missing")
    void testGetDockerCompose_shouldThrowWhenFileMissing() {
        File nonExistentDir = new File("nonexistent-dir-" + System.currentTimeMillis());
        assertFalse(nonExistentDir.exists());

        DockerComposeFileAccessingException exception = assertThrows(
                DockerComposeFileAccessingException.class,
                () -> DockerUtils.getDockerCompose(nonExistentDir)
        );

        assertTrue(exception.getMessage().contains("Error while accessing docker-compose.yml file"));
    }

    @Test
    @DisplayName("getDockerCompose should throw exception on invalid YAML")
    void testGetDockerCompose_shouldThrowOnInvalidYaml() throws IOException {
        Path tempDir = java.nio.file.Files.createTempDirectory("docker-test-invalid");
        File dockerComposeFile = new File(tempDir.toFile(), "docker-compose.yml");

        String invalidContent = "version: '3.8'\nservices: invalid_yaml";
        try (FileOutputStream fos = new FileOutputStream(dockerComposeFile)) {
            fos.write(invalidContent.getBytes(StandardCharsets.UTF_8));
        }

        File finalDir = tempDir.toFile();
        Executable executable = () -> DockerUtils.getDockerCompose(finalDir);

        DockerComposeFileAccessingException exception = assertThrows(
                DockerComposeFileAccessingException.class,
                executable
        );

        assertTrue(exception.getMessage().contains("Error while accessing docker-compose.yml file"));
    }

    @Test
    @DisplayName("constructor should be private and not instantiable")
    void testConstructor_shouldBePrivate() throws Exception {
        var constructor = DockerUtils.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
    }
}
