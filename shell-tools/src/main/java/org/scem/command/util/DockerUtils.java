package org.scem.command.util;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import org.scem.command.model.docker.DockerComposeFile;
import org.yaml.snakeyaml.Yaml;

public class DockerUtils {
   public static DockerComposeFile getDockerCompose(File directory) throws Exception {
      Yaml yaml = new Yaml();
      File file = Paths.get(directory.getAbsolutePath(), "docker-compose.yml").toFile();
      FileInputStream input = new FileInputStream(file);

      DockerComposeFile var5;
      try {
         DockerComposeFile compose = (DockerComposeFile)yaml.loadAs(input, DockerComposeFile.class);
         var5 = compose;
      } catch (Throwable var7) {
         try {
            input.close();
         } catch (Throwable var6) {
            var7.addSuppressed(var6);
         }

         throw var7;
      }

      input.close();
      return var5;
   }
}
