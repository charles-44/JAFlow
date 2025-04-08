package org.scem.command.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StringUtils Test")
class StringUtilsTest {

    @ParameterizedTest(name = "{index} => input=''{0}'', expected=''{1}''")
    @CsvSource({
            "Hello, hello",
            "'', ''",
            "'A', a",
            "already, already",
            "UPPERCASE, uPPERCASE",
            "Special@Characters, special@Characters"
    })
    @DisplayName("lowercaseFirstLetter should return correct result")
    void lowercaseFirstLetter_shouldReturnExpectedResult(String input, String expected) {
        assertEquals(expected, StringUtils.lowercaseFirstLetter(input));
    }

    @Test
    @DisplayName("lowercaseFirstLetter should return null when input is null")
    void lowercaseFirstLetter_shouldReturnNullWhenInputIsNull() {
        assertNull(StringUtils.lowercaseFirstLetter(null));
    }

    @Test
    @DisplayName("private constructor should not be accessible")
    void testPrivateConstructor_shouldNotBeAccessible() throws Exception {
        var constructor = StringUtils.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertNotNull(constructor.newInstance());
    }
}
