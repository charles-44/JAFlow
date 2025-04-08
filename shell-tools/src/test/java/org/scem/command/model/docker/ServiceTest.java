package org.scem.command.model.docker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    @Test
    @DisplayName("should set and get image correctly")
    void testImageGetterAndSetter() {
        Service service = new Service();
        service.setImage("nginx:latest");

        assertEquals("nginx:latest", service.getImage());
    }

    @Test
    @DisplayName("should set and get volumes correctly")
    void testVolumesGetterAndSetter() {
        Service service = new Service();
        List<String> volumes = Arrays.asList("/data:/data", "/logs:/logs");
        service.setVolumes(volumes);

        assertNotNull(service.getVolumes());
        assertEquals(2, service.getVolumes().size());
        assertTrue(service.getVolumes().contains("/data:/data"));
    }

    @Test
    @DisplayName("should set and get environment correctly")
    void testEnvironmentGetterAndSetter() {
        Service service = new Service();
        Map<String, String> env = new HashMap<>();
        env.put("ENV", "prod");
        env.put("DEBUG", "false");

        service.setEnvironment(env);

        assertNotNull(service.getEnvironment());
        assertEquals("prod", service.getEnvironment().get("ENV"));
        assertEquals("false", service.getEnvironment().get("DEBUG"));
    }

    @Test
    @DisplayName("should set and get container_name correctly")
    void testContainerNameGetterAndSetter() {
        Service service = new Service();
        service.setContainer_name("my_container");

        assertEquals("my_container", service.getContainer_name());
    }

    @Test
    @DisplayName("should set and get command correctly")
    void testCommandGetterAndSetter() {
        Service service = new Service();
        service.setCommand("npm start");

        assertEquals("npm start", service.getCommand());
    }

    @Test
    @DisplayName("should set and get ports correctly")
    void testPortsGetterAndSetter() {
        Service service = new Service();
        List<String> ports = Arrays.asList("8080:80", "443:443");
        service.setPorts(ports);

        assertNotNull(service.getPorts());
        assertEquals(2, service.getPorts().size());
        assertTrue(service.getPorts().contains("8080:80"));
    }

    @Test
    @DisplayName("should set and get depends_on correctly")
    void testDependsOnGetterAndSetter() {
        Service service = new Service();
        List<String> dependencies = Arrays.asList("db", "cache");
        service.setDepends_on(dependencies);

        assertNotNull(service.getDepends_on());
        assertEquals(2, service.getDepends_on().size());
        assertTrue(service.getDepends_on().contains("db"));
    }

    @Test
    @DisplayName("should verify equals and hashCode contracts")
    void testEqualsAndHashCode() {
        Service service1 = new Service();
        Service service2 = new Service();

        service1.setImage("nginx");
        service2.setImage("nginx");

        assertEquals(service1, service2);
        assertEquals(service1.hashCode(), service2.hashCode());
    }

    @Test
    @DisplayName("should verify toString is not null and contains expected content")
    void testToString() {
        Service service = new Service();
        service.setImage("redis");

        String result = service.toString();

        assertNotNull(result);
        assertTrue(result.contains("redis"));
    }

    @Test
    @DisplayName("should allow null values in all fields")
    void testAllowsNullValues() {
        Service service = new Service();

        service.setImage(null);
        service.setVolumes(null);
        service.setEnvironment(null);
        service.setContainer_name(null);
        service.setCommand(null);
        service.setPorts(null);
        service.setDepends_on(null);

        assertNull(service.getImage());
        assertNull(service.getVolumes());
        assertNull(service.getEnvironment());
        assertNull(service.getContainer_name());
        assertNull(service.getCommand());
        assertNull(service.getPorts());
        assertNull(service.getDepends_on());
    }
}
