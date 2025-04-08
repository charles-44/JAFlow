package org.scem.command.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void lowercaseFirstLetter_nullInput() {
        assertNull(StringUtils.lowercaseFirstLetter(null));
    }

    @Test
    void lowercaseFirstLetter_emptyInput() {
        assertEquals("", StringUtils.lowercaseFirstLetter(""));
    }

    @Test
    void lowercaseFirstLetter_singleCharacterInput() {
        assertEquals("a", StringUtils.lowercaseFirstLetter("A"));
    }

    @Test
    void lowercaseFirstLetter_multipleCharacterInput() {
        assertEquals("helloWorld", StringUtils.lowercaseFirstLetter("HelloWorld"));
    }

    @Test
    void lowercaseFirstLetter_alreadyLowercase() {
        assertEquals("hello", StringUtils.lowercaseFirstLetter("hello"));
    }
}