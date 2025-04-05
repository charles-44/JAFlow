package org.scem.command.docker;

import java.io.File;

import org.scem.command.base.BaseCommand;
import org.scem.command.exception.ExecutionCommandException;
import org.scem.command.model.docker.DockerComposeFile;
import org.scem.command.util.DockerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;

@Command(
        name = "purge",
        description = {"Stop dockers & remove volumes"}
)
public class DockerPurgeCommand extends BaseCommand implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(DockerPurgeCommand.class);

    public void run() {
        try {
            File directory = this.getProjectDirectory();
            String volumePrefix =  directory.getName().toLowerCase() + "_";
            DockerComposeFile dcFile = DockerUtils.getDockerCompose(directory);
            (new DockerStopCommand()).run();
            dcFile.getVolumes().keySet().forEach(v -> {
                logger.info("Removing volume {}", v);
                this.executeCommand("docker volume rm " + volumePrefix + v );
            });
        } catch (Exception e) {
            throw new ExecutionCommandException("Failed to execute DockerPurgeCommand" , e);
        }
    }

    public Logger getLogger() {
        return logger;
    }
}
