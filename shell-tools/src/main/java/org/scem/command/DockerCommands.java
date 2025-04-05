package org.scem.command;

import org.scem.command.docker.DockerPurgeCommand;
import org.scem.command.docker.DockerStartCommand;
import org.scem.command.docker.DockerStopCommand;
import org.scem.command.docker.DockerTailCommand;
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
    public void run() {
        System.out.println("Use sub command : start | stop | tail | purge | ...");
    }

    public static void main(String[] args) {
        int exitCode = (new CommandLine(new DockerCommands())).execute(args);
        System.exit(exitCode);
    }

}
