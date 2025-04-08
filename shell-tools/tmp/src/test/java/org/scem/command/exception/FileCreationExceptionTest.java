package org.scem.command.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileCreationExceptionTest {

    @Test
    @DisplayName("constructor should set message with absolute file path")
    void testConstructor_shouldSetMessageWithFilePath() {
        File file = new File("test.txt");
        FileCreationException exception = new FileCreationException(file);

        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Error while create file: "));
        assertTrue(exception.getMessage().contains(file.getAbsolutePath()));
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("constructor should handle file with null path gracefully")
    void testConstructor_withFileHavingNullPath_shouldHandleGracefully() {
        File file = new File("") {
            @Override
            public String getAbsolutePath() {
                return null;
            }
        };

        FileCreationException exception = new FileCreationException(file);

        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Error while create file: null"));
    }

    @Test
    @DisplayName("constructor should not throw when file is null")
    void testConstructor_withNullFile_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FileCreationException(null));
    }

}
