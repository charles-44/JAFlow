package org.scem.command.docker;

import org.scem.command.base.BaseCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;

@Command(
   name = "tail",
   description = {"Lance les traces de docker (docker compose tail)"}
)
public class DockerTailCommand extends BaseCommand implements Runnable {
   private static final Logger logger = LoggerFactory.getLogger(DockerTailCommand.class);

   public void run() {
      try {
         this.executeCommand(new String[]{"docker", "compose", "logs", "-f"});
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   public Logger getLogger() {
      return logger;
   }
}
