package org.scem.command;

import org.scem.command.sub.updater.UpdateShellToolsDocumentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

@CommandLine.Command(
        name = "updateDoc",
        mixinStandardHelpOptions = true,
        version = {"updateDoc 1.0"},
        description = {"CLI tool to update documentation"},
        subcommands = {UpdateShellToolsDocumentation.class}
)
public class UpdateDocCommands implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(UpdateDocCommands.class);


    public void run() {
        logger.info("Use sub command : shell-tools | ...");
    }

    public static void main(String[] args) {
        int exitCode = (new CommandLine(new UpdateDocCommands())).execute(args);
        System.exit(exitCode);
    }

}