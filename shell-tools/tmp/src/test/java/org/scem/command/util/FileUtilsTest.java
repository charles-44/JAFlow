package org.scem.command.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    @DisplayName("getProjectDirectory should return current working directory")
    void testGetProjectDirectory_shouldReturnCurrentDir() {
        File projectDir = FileUtils.getProjectDirectory();
        assertNotNull(projectDir);
        assertEquals(System.getProperty("user.dir"), projectDir.getAbsolutePath());
    }

    @Test
    @DisplayName("getRootProjectDirectory should return parent directory of project")
    void testGetRootProjectDirectory_shouldReturnParentDir() {
        File rootDir = FileUtils.getRootProjectDirectory();
        assertNotNull(rootDir);
        assertEquals(new File(System.getProperty("user.dir")).getParentFile().getAbsolutePath(), rootDir.getAbsolutePath());
    }

    @Test
    @DisplayName("findAllFiles should return files with given extension")
    void testFindAllFiles_shouldReturnMatchingFiles() throws IOException {
        Path tempDir = Files.createTempDirectory("testFindFiles");
        Files.createFile(tempDir.resolve("file1.txt"));
        Files.createFile(tempDir.resolve("file2.TXT"));
        Files.createFile(tempDir.resolve("file3.md"));

        List<Path> result = FileUtils.findAllFiles(tempDir, ".txt");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(path -> path.toString().toLowerCase().endsWith(".txt")));
    }

    @Test
    @DisplayName("findAllFiles should return empty list if no matching files")
    void testFindAllFiles_shouldReturnEmptyListWhenNoMatch() throws IOException {
        Path tempDir = Files.createTempDirectory("testFindFilesNone");
        Files.createFile(tempDir.resolve("file1.md"));

        List<Path> result = FileUtils.findAllFiles(tempDir, ".txt");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("findAllFiles should handle extension without leading dot")
    void testFindAllFiles_shouldHandleExtensionWithoutDot() throws IOException {
        Path tempDir = Files.createTempDirectory("testNoDot");
        Files.createFile(tempDir.resolve("readme.md"));

        List<Path> result = FileUtils.findAllFiles(tempDir, "md");

        assertNotNull(result);
        assertEquals(1, result.size());
    }


    @Test
    @DisplayName("getPrivateProperties should throw IOException when file does not exist")
    void testGetPrivateProperties_shouldThrowWhenFileMissing() {
        File missingFile = Paths.get(System.getProperty("user.dir"), "private.properties").toFile();
        missingFile.delete();

        assertThrows(IOException.class, () -> FileUtils.getPrivateProperties("key"));
    }

    @Test
    @DisplayName("private constructor should not be accessible")
    void testPrivateConstructor_shouldNotBeAccessible() throws Exception {
        var constructor = FileUtils.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertNotNull(constructor.newInstance());
    }

}
