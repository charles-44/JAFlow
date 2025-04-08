package org.scem.command.constante;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SubProjectConverterTest {

    private final SubProjectConverter converter = new SubProjectConverter();

    @ParameterizedTest
    @ValueSource(strings = {"shell-tools", "docker", "springboot-workflow", "angular-cli"})
    @DisplayName("convert should return correct SubProject enum for valid value")
    void testConvert_shouldReturnSubProjectForValidValue(String input) {
        SubProject result = converter.convert(input);
        assertNotNull(result);
        assertEquals(input, result.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Shell-Tools", "invalid", "DOCKER ", " springboot-workflow", "angularcli"})
    @DisplayName("convert should throw IllegalArgumentException for unknown values")
    void testConvert_shouldThrowForInvalidValue(String input) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> converter.convert(input));
        assertTrue(exception.getMessage().contains("Unknown SubProject value: " + input));
    }

}
