package org.scem.command.docker;

import java.io.IOException;
import picocli.CommandLine.Command;

@Command(
   name = "stop",
   description = {"Arrête la plateforme Docker (docker compose down)"}
)
public class DockerStopCommand implements Runnable {
   public void run() {
      this.executeCommand("docker", "compose", "down");
   }

   private void executeCommand(String... command) {
      try {
         ProcessBuilder pb = new ProcessBuilder(command);
         pb.inheritIO();
         Process process = pb.start();
         int exitCode = process.waitFor();
         System.out.println("Commande terminée (code " + exitCode + ")");
      } catch (InterruptedException | IOException var5) {
         System.err.println("Erreur : " + var5.getMessage());
      }

   }
}
