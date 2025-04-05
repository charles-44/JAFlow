package org.scem.command.docker;

import java.io.File;
import org.scem.command.base.BaseCommand;
import org.scem.command.model.docker.DockerComposeFile;
import org.scem.command.util.DockerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;

@Command(
   name = "purge",
   description = {"Stop dockers & supprime les volumes"}
)
public class DockerPurgeCommand extends BaseCommand implements Runnable {
   private static final Logger logger = LoggerFactory.getLogger(DockerPurgeCommand.class);

   public void run() {
      try {
         File directory = this.getParentFile();
         DockerComposeFile dcFile = DockerUtils.getDockerCompose(directory);
         (new DockerStopCommand()).run();
         dcFile.getVolumes().keySet().forEach((v) -> {
            logger.info("Supression du volume {}", v);
            String[] var10001 = new String[]{"docker", "volume", "rm", null};
            String var10004 = directory.getName();
            var10001[3] = var10004 + "_" + v;
            this.executeCommand(var10001);
         });
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public Logger getLogger() {
      return logger;
   }
}
