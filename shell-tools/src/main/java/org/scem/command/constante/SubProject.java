package org.scem.command.constante;

import lombok.Getter;

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
}
