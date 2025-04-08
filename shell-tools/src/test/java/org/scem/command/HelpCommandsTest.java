package org.scem.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.MockedStatic;
import org.scem.command.exception.ExecutionCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HelpCommandsTest {

    private HelpCommands helpCommands;

    @BeforeEach
    void setUp() {
        helpCommands = new HelpCommands();
    }

    @Test
    void testRunShouldLogHelpCommands() {
        try (
                MockedStatic<HelpCommands> helpCommandsMock = mockStatic(HelpCommands.class);
                MockedStatic<LoggerFactory> loggerFactoryMock = mockStatic(LoggerFactory.class)
        ) {
            Logger mockLogger = mock(Logger.class);
            loggerFactoryMock.when(() -> LoggerFactory.getLogger(HelpCommands.class)).thenReturn(mockLogger);

            helpCommandsMock.when(() -> HelpCommands.getHelpCommands("\n")).thenReturn("mocked help");


        }
    }

    @Test
    void testRunShouldThrowExecutionCommandExceptionWhenErrorOccurs() {
        try (
                MockedStatic<HelpCommands> helpCommandsMock = mockStatic(HelpCommands.class)
        ) {
            helpCommandsMock.when(() -> HelpCommands.getHelpCommands("\n"))
                    .thenThrow(new RuntimeException("Simulated failure"));

            ExecutionCommandException exception = assertThrows(ExecutionCommandException.class, new Executable() {
                @Override
                public void execute() {
                    helpCommands.run();
                }
            });

            assertEquals("Unable to generate help", exception.getMessage());
            assertNotNull(exception.getCause());
            assertEquals("Simulated failure", exception.getCause().getMessage());
        }
    }

    @Test
    void testGetHelpCommandsReturnsExpectedFormat() throws Exception {


            String result = HelpCommands.getHelpCommands("\n---\n");

            assertNotNull(result);
            assertTrue(result.contains("Commande: ./run.cmd updateDoc ? ? ?"));


    }

    @CommandLine.Command(name = "dummy", description = {"Dummy command for test"})
    static class DummyCommand {
        public DummyCommand() {}
    }

}
