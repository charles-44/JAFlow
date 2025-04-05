package org.scem.command.base;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;

public abstract class BaseCommand {
   public abstract Logger getLogger();

   public File getParentFile() {
      return (new File(System.getProperty("user.dir"))).getParentFile();
   }

   protected int executeCommand(String... command) {
      File parentFile = this.getParentFile();
      return this.executeCommand(parentFile, command);
   }

   protected int executeCommand(File directory, String... command) {
      try {
         this.getLogger().info("Execute command: {}", String.join(" ", command));
         this.getLogger().info("Execute command in {}", directory.getAbsolutePath());
         ProcessBuilder pb = new ProcessBuilder(command);
         pb.directory(directory);
         pb.inheritIO();
         Process process = pb.start();
         int exitCode = process.waitFor();
         this.getLogger().info("Commande terminée (code " + exitCode + ")");
         return exitCode;
      } catch (InterruptedException | IOException var6) {
         this.getLogger().error("Erreur : " + var6.getMessage());
         return 1;
      }
   }
}
