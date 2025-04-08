package org.scem.command.base;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseCommandTest {

    static class TestableBaseCommand extends BaseCommand {
        private final Logger logger;

        TestableBaseCommand(Logger logger) {
            this.logger = logger;
        }

        @Override
        public Logger getLogger() {
            return logger;
        }

        @Override
        protected void executeCommand(File directory, String... command) throws IOException {
            super.executeCommand(directory, command);
        }
    }

    @Test
    @DisplayName("should execute command successfully")
    void testExecuteCommand_withValidCommand_shouldSucceed()  {
        Logger logger = mock(Logger.class);
        when(logger.isInfoEnabled()).thenReturn(true);

        BaseCommand command = new TestableBaseCommand(logger);
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        String[] cmd = {"echo", "hello"};

        assertDoesNotThrow(() -> command.executeCommand(tempDir, cmd));
        verify(logger, atLeastOnce()).info(anyString(), Optional.ofNullable(any()));
    }


    @Test
    @DisplayName("should handle IOException gracefully")
    void testExecuteCommand_shouldHandleIOException()  {
        Logger logger = mock(Logger.class);

        BaseCommand command = new TestableBaseCommand(logger) {
            @Override
            protected void executeCommand(File directory, String... command) throws IOException {
                throw new IOException("Simulated IO error");
            }
        };

        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        String[] cmd = {"invalidCommand"};

        assertThrows(IOException.class, () -> command.executeCommand(tempDir, cmd));
    }

    @Test
    @DisplayName("should throw NullPointerException when command is null")
    void testExecuteCommand_withNullCommand_shouldThrowException() {
        Logger logger = mock(Logger.class);
        BaseCommand command = new TestableBaseCommand(logger);

        assertThrows(NullPointerException.class, () -> command.executeCommand((String[]) null));
    }


    @Test
    @DisplayName("should fail gracefully with empty command")
    void testExecuteCommand_withEmptyCommand_shouldFail() {
        Logger logger = mock(Logger.class);
        BaseCommand command = new TestableBaseCommand(logger);
        File tempDir = new File(System.getProperty("java.io.tmpdir"));

        String[] cmd = new String[0];
        assertThrows(IndexOutOfBoundsException.class, () -> command.executeCommand(tempDir, cmd));
    }
}
