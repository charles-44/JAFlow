package org.scem.command.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExecutionCommandExceptionTest {

    @Test
    @DisplayName("constructor with message should set correct message")
    void testConstructorWithMessage() {
        String message = "Error during execution";
        ExecutionCommandException exception = new ExecutionCommandException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("constructor with message and cause should set both correctly")
    void testConstructorWithMessageAndCause() {
        String message = "Execution failed";
        Throwable cause = new RuntimeException("Underlying failure");
        ExecutionCommandException exception = new ExecutionCommandException(message, cause);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("constructor with null message and null cause should be allowed")
    void testConstructorWithNullMessageAndNullCause() {
        ExecutionCommandException exception = new ExecutionCommandException(null, null);

        assertNotNull(exception);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("constructor with null message should return null message")
    void testConstructorWithNullMessage() {
        ExecutionCommandException exception = new ExecutionCommandException((String) null);

        assertNotNull(exception);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }
}
