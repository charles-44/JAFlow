package org.scem.command.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.scem.command.constante.SubProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DocumentationBaseCommandTest {

    @TempDir
    Path tempDir;

    static class TestableDocumentationBaseCommand extends DocumentationBaseCommand {
        private final SubProject subProject;

        TestableDocumentationBaseCommand(SubProject subProject) {
            this.subProject = subProject;
        }

        @Override
        public org.slf4j.Logger getLogger() {
            return mock(org.slf4j.Logger.class);
        }

        @Override
        public SubProject getSubProject() {
            return subProject;
        }
    }

    @BeforeEach
    void setUp() {
        System.setProperty("user.dir", tempDir.toFile().getAbsolutePath());
    }

    private SubProject getMockedSubProject() {
        SubProject mockSubProject = mock(SubProject.class);
        when(mockSubProject.getValue()).thenReturn(tempDir.toFile().getName());
        return  mockSubProject;
    }

    @Test
    @DisplayName("getSubProjectReadme should create README.md if it does not exist")
    void testGetSubProjectReadme_createsFileIfNotExist() throws IOException {
        DocumentationBaseCommand command = new TestableDocumentationBaseCommand(getMockedSubProject());
        File readme = command.getSubProjectReadme();

        assertNotNull(readme);
        assertTrue(readme.exists());
        assertEquals("README.md", readme.getName());
    }

    @Test
    @DisplayName("getSubProjectReadme should return existing README.md")
    void testGetSubProjectReadme_returnsExistingFile() throws IOException {
        Path readmePath = tempDir.resolve("README.md");
        Files.writeString(readmePath, "# Existing README");

        DocumentationBaseCommand command = new TestableDocumentationBaseCommand(getMockedSubProject());
        File readme = command.getSubProjectReadme();

        assertNotNull(readme);
        assertTrue(readme.exists());
        assertEquals(readmePath.toFile().getAbsolutePath(), readme.getAbsolutePath());
    }

    @Test
    @DisplayName("getSubProjectReadme should throw FileCreationException if file creation fails")
    void testGetSubProjectReadme_throwsFileCreationException() {
        SubProject mockSubProject = mock(SubProject.class);
        when(mockSubProject.getValue()).thenReturn("invalid<>name"); // invalid path for most OS

        DocumentationBaseCommand command = new TestableDocumentationBaseCommand(mockSubProject);

        assertThrows(InvalidPathException.class, command::getSubProjectReadme);
    }

    @Test
    @DisplayName("replaceInReadme should insert content between delimiters")
    void testReplaceInReadme_shouldReplaceContentBetweenDelimiters() throws IOException {
        DocumentationBaseCommand command = new TestableDocumentationBaseCommand(getMockedSubProject());
        Files.writeString(command.getSubProjectReadme().toPath(), "HEADER\n<!-- START_BLOCK --><!-- END_BLOCK -->\nFOOTER");

        String contentToInsert = "New content inside block";

        File updatedReadme = command.replaceInReadme("BLOCK", contentToInsert);

        assertNotNull(updatedReadme);
        assertTrue(updatedReadme.exists());

        String updatedContent = Files.readString(updatedReadme.toPath());
        assertTrue(updatedContent.contains("<!-- START_BLOCK -->"));
        assertTrue(updatedContent.contains("<!-- END_BLOCK -->"));
        assertTrue(updatedContent.contains(contentToInsert));
    }

    @Test
    @DisplayName("replaceInReadme should add delimiters if not present")
    void testReplaceInReadme_shouldAddDelimitersIfAbsent() throws IOException {

        DocumentationBaseCommand command = new TestableDocumentationBaseCommand(getMockedSubProject());
        Files.writeString(command.getSubProjectReadme().toPath(), "Initial content without delimiters.");


        String contentToInsert = "Inserted block";

        File updatedReadme = command.replaceInReadme("BLOCK", contentToInsert);

        assertNotNull(updatedReadme);
        String result = Files.readString(updatedReadme.toPath());

        assertTrue(result.contains("<!-- START_BLOCK -->"));
        assertTrue(result.contains("<!-- END_BLOCK -->"));
        assertTrue(result.contains(contentToInsert));
    }
}
