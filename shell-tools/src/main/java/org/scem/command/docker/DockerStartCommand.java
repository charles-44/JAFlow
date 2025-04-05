package org.scem.command.docker;

import org.scem.command.base.BaseCommand;
import org.scem.command.model.docker.DockerComposeFile;
import org.scem.command.util.DockerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;

@Command(
   name = "start",
   description = {"Démarre la plateforme Docker (docker compose up -d)"}
)
public class DockerStartCommand extends BaseCommand implements Runnable {
   private static final Logger logger = LoggerFactory.getLogger(DockerStartCommand.class);

   public void run() {
      try {
         DockerComposeFile dcFile = DockerUtils.getDockerCompose(this.getParentFile());
         dcFile.getVolumes().keySet().forEach((v) -> {
            logger.info("Creation du volume {}", v);
         });
         this.executeCommand(new String[]{"docker", "compose", "up", "-d"});
         (new DockerTailCommand()).run();
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   public Logger getLogger() {
      return logger;
   }
}
