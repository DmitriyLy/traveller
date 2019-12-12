package org.dmly.traveller.presentation.admin.client;

import org.dmly.traveller.app.infra.environment.Environment;
import org.dmly.traveller.common.infra.http.RestClient;
import org.dmly.traveller.geography.dto.CityDTO;

import javax.inject.Inject;
import java.util.List;

public class CityClient {

    private final String baseUrl;

    private final RestClient restClient;

    @Inject
    public CityClient(Environment env, RestClient restClient) {
        this.baseUrl = env.getProperty("client.geography.url") + "/cities";
        this.restClient = restClient;
    }

    public List<CityDTO> findAll() {
        return restClient.get(baseUrl, List.class).getBody();
    }

    public CityDTO create(CityDTO city) {
        return restClient.post(baseUrl, CityDTO.class, city).getBody();
    }
}
