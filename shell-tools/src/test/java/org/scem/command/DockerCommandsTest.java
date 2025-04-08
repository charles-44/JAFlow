package org.scem.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DockerCommands Test")
class DockerCommandsTest {

    @Test
    @DisplayName("run should log default subcommand usage message")
    void run_shouldLogDefaultUsageMessage() {
        DockerCommands dockerCommands = new DockerCommands();
        assertDoesNotThrow(dockerCommands::run);
    }


    @Test
    @DisplayName("CommandLine should parse with known subcommands")
    void commandLine_shouldRecognizeSubcommands() {
        CommandLine cmd = new CommandLine(new DockerCommands());
        assertNotNull(cmd.getSubcommands().get("start"));
        assertNotNull(cmd.getSubcommands().get("stop"));
        assertNotNull(cmd.getSubcommands().get("tail"));
        assertNotNull(cmd.getSubcommands().get("purge"));
    }

    @Test
    @DisplayName("CommandLine should return exit code 0 for valid command with help")
    void commandLine_shouldReturnExitCodeZeroForHelp() {
        int exitCode = new CommandLine(new DockerCommands()).execute("--help");
        assertEquals(0, exitCode);
    }

    @Test
    @DisplayName("CommandLine should return non-zero exit code for unknown subcommand")
    void commandLine_shouldReturnNonZeroForUnknownCommand() {
        int exitCode = new CommandLine(new DockerCommands()).execute("unknown");
        assertNotEquals(0, exitCode);
    }

}
