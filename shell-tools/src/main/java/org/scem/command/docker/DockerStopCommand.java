package org.scem.command.docker;


import org.scem.command.base.BaseCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;

@Command(
   name = "stop",
   description = {"Stops the Docker platform (docker compose down)"}
)
public class DockerStopCommand extends BaseCommand  implements Runnable {

   private static final Logger logger = LoggerFactory.getLogger(DockerStopCommand.class);


   public void run() {
      this.executeCommand("docker", "compose", "down");
   }

   @Override
   public Logger getLogger() {
      return logger;
   }
}
