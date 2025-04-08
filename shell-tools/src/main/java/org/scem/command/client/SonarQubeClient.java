package org.scem.command.client;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.scem.command.util.FileUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SonarQubeClient {

    private final String sonarUrl;
    private final String projectKey;
    private final String token;

    public SonarQubeClient(String sonarUrl, String projectKey, String token) {
        this.sonarUrl = sonarUrl;
        this.projectKey = projectKey;
        this.token = token;
    }

    public static SonarQubeClient getSonarQubeClient(String projectKey) throws IOException {
        String sonarUrl = FileUtils.getPrivateProperties("sonar.host.url");
        String token = FileUtils.getPrivateProperties("sonar.user.token");
        return new SonarQubeClient(sonarUrl, projectKey, token);
    }

    public Map<String,String> fetchMetrics() throws IOException {
        Map<String,String> result = new HashMap<>();
        OkHttpClient client = new OkHttpClient();

        String metrics = String.join(",",
                "sqale_rating",
                "reliability_rating",
                "security_rating",
                "coverage",
                "duplicated_lines_density",
                "alert_status",
                "bugs",
                "vulnerabilities",
                "code_smells",
                "security_hotspots",
                "ncloc" // number of lines of code
        );

        String url = sonarUrl + "/api/measures/component?component=" + projectKey + "&metricKeys=" + metrics;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code: " + response);

            String body = response.body().string();
            JsonObject json = JsonParser.parseString(body).getAsJsonObject();
            JsonArray measures = json
                    .getAsJsonObject("component")
                    .getAsJsonArray("measures");

            for (JsonElement element : measures) {
                JsonObject metric = element.getAsJsonObject();
                String name = metric.get("metric").getAsString();
                String value = metric.get("value").getAsString();
                result.put(name,value);

            }
        }
        return result;
    }
}
