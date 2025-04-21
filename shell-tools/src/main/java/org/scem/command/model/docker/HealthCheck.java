package org.scem.command.model.docker;

import lombok.Data;

import java.util.List;

@Data
public class HealthCheck {

    private List<String> test;
    private String interval;
    private String timeout;
    private String retries;
}
