package org.scem.command;

import org.scem.command.document.updater.UpdateShellToolsDocumentation;
import picocli.CommandLine;

@CommandLine.Command(
        name = "updateDoc",
        mixinStandardHelpOptions = true,
        version = {"updateDoc 1.0"},
        description = {"CLI tool to update documentation"},
        subcommands = {UpdateShellToolsDocumentation.class}
)
public class UpdateDocCommands implements Runnable {
    public void run() {
        System.out.println("Use sub command : shell-tools | ...");
    }

    public static void main(String[] args) {
        int exitCode = (new CommandLine(new UpdateDocCommands())).execute(args);
        System.exit(exitCode);
    }

}