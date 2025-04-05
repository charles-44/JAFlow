package org.scem.command.model.docker;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.Generated;

@Data
public class Service {
   private String image;
   private List<String> volumes;
   private Map<String, String> environment;
   private String container_name;
   private String command;
   private List<String> ports;
   private List<String> depends_on;




}
