package org.scem.command.model.docker;

import java.util.Map;

import lombok.Data;

@Data
public class DockerComposeFile {
   private String version;
   private Map<String, Service> services;
   private Map<String, Object> volumes;
   private Map<String, Object> networks;
}
