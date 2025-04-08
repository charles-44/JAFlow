package org.scem.command.model.docker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DockerComposeFileTest {

    @Test
    @DisplayName("should set and get version correctly")
    void testVersionGetterAndSetter() {
        DockerComposeFile file = new DockerComposeFile();
        file.setVersion("3.8");

        assertEquals("3.8", file.getVersion());
    }

    @Test
    @DisplayName("should set and get services correctly")
    void testServicesGetterAndSetter() {
        DockerComposeFile file = new DockerComposeFile();
        Map<String, Service> services = new HashMap<>();
        Service service = new Service(); 
        services.put("web", service);
        file.setServices(services);

        assertNotNull(file.getServices());
        assertEquals(1, file.getServices().size());
        assertSame(service, file.getServices().get("web"));
    }

    @Test
    @DisplayName("should set and get volumes correctly")
    void testVolumesGetterAndSetter() {
        DockerComposeFile file = new DockerComposeFile();
        Map<String, Object> volumes = new HashMap<>();
        volumes.put("db-data", new Object());
        file.setVolumes(volumes);

        assertNotNull(file.getVolumes());
        assertEquals(1, file.getVolumes().size());
        assertTrue(file.getVolumes().containsKey("db-data"));
    }

    @Test
    @DisplayName("should set and get networks correctly")
    void testNetworksGetterAndSetter() {
        DockerComposeFile file = new DockerComposeFile();
        Map<String, Object> networks = new HashMap<>();
        networks.put("backend", new Object());
        file.setNetworks(networks);

        assertNotNull(file.getNetworks());
        assertEquals(1, file.getNetworks().size());
        assertTrue(file.getNetworks().containsKey("backend"));
    }

    @Test
    @DisplayName("should verify equals and hashCode contracts")
    void testEqualsAndHashCode() {
        DockerComposeFile file1 = new DockerComposeFile();
        DockerComposeFile file2 = new DockerComposeFile();

        file1.setVersion("3.9");
        file2.setVersion("3.9");

        assertEquals(file1, file2);
        assertEquals(file1.hashCode(), file2.hashCode());
    }

    @Test
    @DisplayName("should verify toString is not null and contains version")
    void testToString() {
        DockerComposeFile file = new DockerComposeFile();
        file.setVersion("2.4");

        String result = file.toString();

        assertNotNull(result);
        assertTrue(result.contains("2.4"));
    }

    @Test
    @DisplayName("should allow null values in all fields")
    void testAllowsNullValues() {
        DockerComposeFile file = new DockerComposeFile();

        file.setVersion(null);
        file.setServices(null);
        file.setVolumes(null);
        file.setNetworks(null);

        assertNull(file.getVersion());
        assertNull(file.getServices());
        assertNull(file.getVolumes());
        assertNull(file.getNetworks());
    }
}
