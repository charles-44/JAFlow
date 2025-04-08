package org.scem.command;

import org.scem.command.sub.docker.DockerPurgeCommand;
import org.scem.command.sub.docker.DockerStartCommand;
import org.scem.command.sub.docker.DockerStopCommand;
import org.scem.command.sub.docker.DockerTailCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        name = "docker",
        mixinStandardHelpOptions = true,
        version = {"docker 1.0"},
        description = {"CLI tool to control Docker Compose"},
        subcommands = {DockerStartCommand.class, DockerStopCommand.class, DockerTailCommand.class, DockerPurgeCommand.class}
)
public class DockerCommands implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(DockerCommands.class);

    public void run() {
        logger.info("Use sub command : start | stop | tail | purge | ...");
    }

    public static void main(String[] args) {
        int exitCode = (new CommandLine(new DockerCommands())).execute(args);
        System.exit(exitCode);
    }

}
