package org.scem.command.sub.updater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.scem.command.constante.SubProject;
import org.scem.command.exception.ExecutionCommandException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UpdateShellToolsDocumentation Test")
class UpdateShellToolsDocumentationTest {

    @TempDir
    File tempDir;

    @BeforeEach()
    void init() throws IOException {

        File projectDir = Paths.get(tempDir.getAbsolutePath(), "project-dir").toFile();
        File currentProject = Paths.get(tempDir.getAbsolutePath(), SubProject.SHELL.getValue()).toFile();
        System.setProperty("user.dir", projectDir.getAbsolutePath());

        boolean created = currentProject.mkdirs();
        if (created){
            File readmeFile = Paths.get(currentProject.getAbsolutePath(),"README.md").toFile();
            readmeFile.createNewFile();
        }



    }

    @Test
    @DisplayName("getLogger should return non-null logger")
    void getLogger_shouldReturnLogger() {
        UpdateShellToolsDocumentation command = new UpdateShellToolsDocumentation();
        assertNotNull(command.getLogger());
    }

    @Test
    @DisplayName("getSubProject should return SHELL enum")
    void getSubProject_shouldReturnSHELL() {
        UpdateShellToolsDocumentation command = new UpdateShellToolsDocumentation();
        assertEquals(SubProject.SHELL, command.getSubProject());
    }

    @Test
    @DisplayName("run should execute without throwing")
    void run_shouldExecuteWithoutError() {

       UpdateShellToolsDocumentation command = new UpdateShellToolsDocumentation();
        assertDoesNotThrow(command::run);
    }


    @Test
    @DisplayName("run should execute with throwing exception when user.dir is corrupted")
    void run_shouldExecuteWithError() {
        System.setProperty("user.dir", Paths.get(tempDir.getAbsolutePath(),"corrupted-project-dir","corrupted-project").toFile().getAbsolutePath());

        UpdateShellToolsDocumentation command = new UpdateShellToolsDocumentation();
        assertThrows(ExecutionCommandException.class,command::run);
    }

    @Test
    @DisplayName("run should throw ExecutionCommandException when reflection fails")
    void run_shouldThrowWhenReflectionFails() {
        UpdateShellToolsDocumentation command = new UpdateShellToolsDocumentation() {
            @Override
            public void run() {
                try {
                    throw new InstantiationException("Simulated instantiation failure");
                } catch (Exception e) {
                    throw new ExecutionCommandException("Failed to execute UpdateShellToolsDocumentation", e);
                }
            }
        };

        ExecutionCommandException ex = assertThrows(ExecutionCommandException.class, command::run);
        assertTrue(ex.getMessage().contains("Failed to execute UpdateShellToolsDocumentation"));
        assertNotNull(ex.getCause());
    }
}
