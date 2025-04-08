package org.scem.command.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DockerComposeFileAccessingExceptionTest {

    @Test
    @DisplayName("constructor with message should set correct message")
    void testConstructorWithMessage() {
        String message = "Access denied to docker-compose file";
        DockerComposeFileAccessingException exception = new DockerComposeFileAccessingException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("constructor with message and cause should set both correctly")
    void testConstructorWithMessageAndCause() {
        String message = "Failed to read docker-compose file";
        Throwable cause = new RuntimeException("I/O error");
        DockerComposeFileAccessingException exception = new DockerComposeFileAccessingException(message, cause);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("constructor with null message and null cause should be allowed")
    void testConstructorWithNullMessageAndCause() {
        DockerComposeFileAccessingException exception = new DockerComposeFileAccessingException(null, null);

        assertNotNull(exception);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("constructor with null message should return null message and no cause")
    void testConstructorWithNullMessage() {
        DockerComposeFileAccessingException exception = new DockerComposeFileAccessingException((String) null);

        assertNotNull(exception);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }
}
