package org.scem.command.exception;

public class DockerComposeFileAccessingException extends RuntimeException {
    public DockerComposeFileAccessingException(String message) {
        super(message);
    }

  public DockerComposeFileAccessingException(String message, Throwable cause) {
    super(message, cause);
  }
}
