package org.scem.command.sub.docker;


import org.scem.command.base.BaseCommand;
import org.scem.command.exception.ExecutionCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;

import java.io.IOException;

@Command(
   name = "stop",
   description = {"Stops the Docker platform (docker compose down)"}
)
public class DockerStopCommand extends BaseCommand  implements Runnable {

   private static final Logger logger = LoggerFactory.getLogger(DockerStopCommand.class);


   public void run() {
       try {
           this.executeCommand("docker", "compose", "down");
       } catch (IOException e) {
          throw new ExecutionCommandException("Failed to stop docker compose project" , e);
       }
   }

   @Override
   public Logger getLogger() {
      return logger;
   }
}
