package org.scem.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UpdateDocCommands Test")
class UpdateDocCommandsTest {

    @Test
    @DisplayName("run should log default usage message")
    void run_shouldLogDefaultMessage() {
        UpdateDocCommands command = new UpdateDocCommands();
        assertDoesNotThrow(command::run);

    }


    @Test
    @DisplayName("CommandLine should recognize subcommand updateShellToolsDocumentation")
    void commandLine_shouldRecognizeSubcommand() {
        CommandLine cmd = new CommandLine(new UpdateDocCommands());
        assertNotNull(cmd.getSubcommands().get("shell"));
    }

    @Test
    @DisplayName("CommandLine should return exit code 0 for --help")
    void commandLine_shouldReturnZeroForHelp() {
        int exitCode = new CommandLine(new UpdateDocCommands()).execute("--help");
        assertEquals(0, exitCode);
    }

    @Test
    @DisplayName("CommandLine should return non-zero exit code for unknown subcommand")
    void commandLine_shouldReturnNonZeroForUnknownSubcommand() {
        int exitCode = new CommandLine(new UpdateDocCommands()).execute("invalidSubcommand");
        assertNotEquals(0, exitCode);
    }
}
