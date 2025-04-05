package org.scem.command.model.docker;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Service {
   private String image;
   private List<String> volumes;
   private Map<String, String> environment;
   @SuppressWarnings({"java:S116","java:S117"}) // Non-compliant field naming (Sonar rule ID)
   private String container_name;
   private String command;
   private List<String> ports;
   @SuppressWarnings({"java:S116","java:S117"}) // Non-compliant field naming (Sonar rule ID)
   private List<String> depends_on;
}
