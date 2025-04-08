package org.scem.command.sub.docker;

import org.scem.command.base.BaseCommand;
import org.scem.command.exception.ExecutionCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;

@Command(
   name = "tail",
   description = {"Starts Docker logs (docker compose logs -f)"}
)
public class DockerTailCommand extends BaseCommand implements Runnable {
   private static final Logger logger = LoggerFactory.getLogger(DockerTailCommand.class);

   public void run() {
      try {
         this.executeCommand("docker", "compose", "logs", "-f");
      } catch (Exception e) {
         throw new ExecutionCommandException("Failed to execute DockerTailCommand" , e);
      }
   }

   public Logger getLogger() {
      return logger;
   }
}
