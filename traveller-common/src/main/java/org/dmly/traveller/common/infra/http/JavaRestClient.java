package org.dmly.traveller.common.infra.http;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
public class JavaRestClient implements RestClient {

    private static final String CONTENT_TYPE_JSON = "application/json";

    private final HttpClient client;

    private final int timeout;


    public JavaRestClient(int timeout) {
        client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        this.timeout = timeout;
    }

    @Override
    public <T> RestResponse<T> get(String url, Class<T> clz) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(timeout))
                .header("Accept", CONTENT_TYPE_JSON)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            return null;
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
