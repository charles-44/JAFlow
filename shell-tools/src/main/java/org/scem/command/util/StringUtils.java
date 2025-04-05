package org.scem.command.util;

public class StringUtils {

    public static String lowercaseFirstLetter(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toLowerCase() + input.substring(1);
    }
}
