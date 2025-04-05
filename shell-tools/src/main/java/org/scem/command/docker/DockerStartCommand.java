package org.scem.command.docker;

import org.scem.command.base.BaseCommand;
import org.scem.command.model.docker.DockerComposeFile;
import org.scem.command.util.DockerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;

@Command(
   name = "start",
   description = {"Starts the Docker platform (docker compose up -d)"}
)
public class DockerStartCommand extends BaseCommand implements Runnable {
   private static final Logger logger = LoggerFactory.getLogger(DockerStartCommand.class);

   public void run() {
      try {
         this.executeCommand("docker compose up -d");
         (new DockerTailCommand()).run();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   public Logger getLogger() {
      return logger;
   }
}
