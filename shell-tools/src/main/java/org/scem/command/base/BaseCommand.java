package org.scem.command.base;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;

public abstract class BaseCommand {
    public abstract Logger getLogger();

    public File getProjectDirectory() {
        return (new File(System.getProperty("user.dir"))).getParentFile();
    }

    protected void executeCommand(String... command) {
        File parentFile = this.getProjectDirectory();
        this.executeCommand(parentFile, command);
    }

    protected void executeCommand(File directory, String... command) {
        try {
            if (this.getLogger().isInfoEnabled()) {
                this.getLogger().info("Execute command: {}", String.join(" ", command));
                this.getLogger().info("Execute command in {}", directory.getAbsolutePath());
            }
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(directory);
            pb.inheritIO();
            Process process = pb.start();
            int exitCode = process.waitFor();
            if (this.getLogger().isInfoEnabled()) {
                this.getLogger().info("Command finished (code {})", exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // ✅ signaler l'interruption
            this.getLogger().error("Command interrupted: {}", e.getMessage());
        } catch (IOException e) {
            this.getLogger().error("I/O error while executing command: {}", e.getMessage());
        }
    }
}
