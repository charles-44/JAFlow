package org.scem.command.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    @DisplayName("lowercaseFirstLetter should convert first letter to lowercase")
    void testLowercaseFirstLetter_shouldConvertFirstLetter() {
        String input = "Hello";
        String result = StringUtils.lowercaseFirstLetter(input);
        
        assertEquals("hello", result);
    }

    @Test
    @DisplayName("lowercaseFirstLetter should return empty string when input is empty")
    void testLowercaseFirstLetter_shouldReturnEmptyWhenEmpty() {
        String input = "";
        String result = StringUtils.lowercaseFirstLetter(input);
        
        assertEquals("", result);
    }

    @Test
    @DisplayName("lowercaseFirstLetter should return null when input is null")
    void testLowercaseFirstLetter_shouldReturnNullWhenNull() {
        String result = StringUtils.lowercaseFirstLetter(null);
        
        assertNull(result);
    }

    @Test
    @DisplayName("lowercaseFirstLetter should handle single character string")
    void testLowercaseFirstLetter_shouldHandleSingleChar() {
        String input = "A";
        String result = StringUtils.lowercaseFirstLetter(input);
        
        assertEquals("a", result);
    }

    @Test
    @DisplayName("lowercaseFirstLetter should not change already lowercase first letter")
    void testLowercaseFirstLetter_shouldNotChangeAlreadyLowercase() {
        String input = "already";
        String result = StringUtils.lowercaseFirstLetter(input);
        
        assertEquals("already", result);
    }

    @Test
    @DisplayName("lowercaseFirstLetter should handle string with all uppercase letters")
    void testLowercaseFirstLetter_shouldHandleAllUppercase() {
        String input = "UPPERCASE";
        String result = StringUtils.lowercaseFirstLetter(input);
        
        assertEquals("uPPERCASE", result);
    }

    @Test
    @DisplayName("lowercaseFirstLetter should handle string with special characters")
    void testLowercaseFirstLetter_shouldHandleSpecialChars() {
        String input = "Special@Characters";
        String result = StringUtils.lowercaseFirstLetter(input);
        
        assertEquals("special@Characters", result);
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