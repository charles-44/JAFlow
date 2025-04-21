package org.scem.command.model.docker;

import lombok.Data;

@Data
public class Build {
    private  String context;
    private String dockerfile;
}
