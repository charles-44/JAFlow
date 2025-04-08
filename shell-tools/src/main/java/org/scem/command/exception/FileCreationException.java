package org.scem.command.exception;

import java.io.File;
import java.io.IOException;

public class FileCreationException extends IOException {
    public FileCreationException(File file) {
        super("Error while create file: " + file.getAbsolutePath());
    }
}
