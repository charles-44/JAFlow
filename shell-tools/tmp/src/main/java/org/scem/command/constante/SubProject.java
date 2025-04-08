package org.scem.command.constante;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SubProject {
    SHELL("shell-tools"),
    DOCKER("docker"),
    SPRINGBOOT("springboot-workflow"),
    ANGULAR("angular-cli");

    private final String value;

    SubProject(String value) {
        this.value = value;
    }


    public static SubProject fromValue(String value) {
        return Arrays.stream(SubProject.values())
                .filter(sp -> sp.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown SubProject value: " + value));
    }
}
