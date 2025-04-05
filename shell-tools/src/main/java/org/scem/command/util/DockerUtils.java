package org.scem.command.util;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;

import org.scem.command.exception.DockerComposeFileAccessingException;
import org.scem.command.model.docker.DockerComposeFile;
import org.yaml.snakeyaml.Yaml;

public class DockerUtils {

   private DockerUtils(){
      //private constructor to avoid instantiation
   }

   public static DockerComposeFile getDockerCompose(File directory) throws DockerComposeFileAccessingException {

     try {
        Yaml yaml = new Yaml();
        File file = Paths.get(directory.getAbsolutePath(), "docker-compose.yml").toFile();
        FileInputStream input = new FileInputStream(file);
        return yaml.loadAs(input, DockerComposeFile.class);
     } catch (Exception e) {
        throw new DockerComposeFileAccessingException("Error while accessing docker-compose.yml file in " + directory.getAbsolutePath(),e);
     }
   }
}
