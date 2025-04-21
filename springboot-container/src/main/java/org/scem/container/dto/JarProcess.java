package org.scem.container.dto;

import lombok.Data;

@Data
public class JarProcess {
    private long pid;
    private String filepath;
    private String dirPath;
    private String name;
    private String commandLine;
    private Boolean destroyed = false;
    private String artifactId;

}
