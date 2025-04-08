package org.scem.command.client;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.scem.command.util.FileUtils;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class SonarQubeClientTest {

    private MockWebServer mockWebServer;
    private SonarQubeClient sonarQubeClient;
    private MockedStatic<FileUtils> fileUtilsMockedStatic;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        fileUtilsMockedStatic = mockStatic(FileUtils.class);
        fileUtilsMockedStatic.when(() -> FileUtils.getPrivateProperties("sonar.host.url"))
                .thenReturn(mockWebServer.url("/").toString());
        fileUtilsMockedStatic.when(() -> FileUtils.getPrivateProperties("sonar.user.token"))
                .thenReturn("testToken");

        sonarQubeClient = SonarQubeClient.getSonarQubeClient("testProjectKey");
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
        fileUtilsMockedStatic.close();
    }

    @Test
    void testFetchMetrics_success() throws IOException {
        String mockResponse = "{\"component\":{\"measures\":[{\"metric\":\"bugs\",\"value\":\"0\"},{\"metric\":\"vulnerabilities\",\"value\":\"0\"}]}}";
        mockWebServer.enqueue(new MockResponse().setBody(mockResponse).addHeader("Content-Type", "application/json"));

        Map<String, String> metrics = sonarQubeClient.fetchMetrics();

        assertEquals("0", metrics.get("bugs"));
        assertEquals("0", metrics.get("vulnerabilities"));
    }

    @Test
    void testGetSonarQubeClient()  {
        assertNotNull(sonarQubeClient);
    }

    @Test
    void testFetchMetrics_emptyResponse() throws IOException {
        String mockResponse = "{\"component\":{\"measures\":[]}}";
        mockWebServer.enqueue(new MockResponse().setBody(mockResponse).addHeader("Content-Type", "application/json"));

        Map<String, String> metrics = sonarQubeClient.fetchMetrics();

        assertTrue(metrics.isEmpty());
    }

    @Test
    void testFetchMetrics_unsuccessfulResponse() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        assertThrows(IOException.class, () -> sonarQubeClient.fetchMetrics());
    }
}