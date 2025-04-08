package org.scem.command.constante;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SubProjectTest {

    @ParameterizedTest
    @ValueSource(strings = {"shell-tools", "docker", "springboot-workflow", "angular-cli"})
    @DisplayName("fromValue should return correct enum constant for valid input")
    void testFromValue_shouldReturnEnumForValidInput(String input) {
        SubProject result = SubProject.fromValue(input);
        assertNotNull(result);
        assertEquals(input, result.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"unknown", "Shell-Tools", "DOCKER ", " springboot-workflow", "angularcli"})
    @DisplayName("fromValue should throw IllegalArgumentException for invalid input")
    void testFromValue_shouldThrowForInvalidInput(String input) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> SubProject.fromValue(input));
        assertTrue(exception.getMessage().contains("Unknown SubProject value: " + input));
    }


    @Test
    @DisplayName("getValue should return correct string representation")
    void testGetValue_shouldReturnCorrectValue() {
        assertEquals("shell-tools", SubProject.SHELL.getValue());
        assertEquals("docker", SubProject.DOCKER.getValue());
        assertEquals("springboot-workflow", SubProject.SPRINGBOOT.getValue());
        assertEquals("angular-cli", SubProject.ANGULAR.getValue());
    }

}
