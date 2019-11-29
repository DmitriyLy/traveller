package org.dmly.traveller.common.infra.http;

import lombok.extern.slf4j.Slf4j;
import org.dmly.traveller.common.infra.json.JsonClient;

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

    private final JsonClient jsonClient;

    public JavaRestClient(int timeout, final JsonClient jsonClient) {
        client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        this.timeout = timeout;
        this.jsonClient = jsonClient;
    }

    @Override
    public <T> RestResponse<T> get(final String url, final Class<T> clz) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(timeout))
                .header("Accept", CONTENT_TYPE_JSON)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            return new RestResponse<T>(response.statusCode(), jsonClient.fromJson(body, clz));
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
