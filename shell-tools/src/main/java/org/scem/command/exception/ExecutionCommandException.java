package org.scem.command.exception;

public class ExecutionCommandException extends RuntimeException {
    public ExecutionCommandException(String message) {
        super(message);
    }

    public ExecutionCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
