package org.dmly.traveller.presentation.admin.config;

import org.apache.commons.lang3.math.NumberUtils;
import org.dmly.traveller.app.infra.environment.Environment;
import org.dmly.traveller.app.infra.environment.StandardPropertyEnvironment;
import org.dmly.traveller.common.infra.http.JavaRestClient;
import org.dmly.traveller.common.infra.http.RestClient;
import org.dmly.traveller.common.infra.json.GsonJsonClient;
import org.dmly.traveller.common.infra.json.JsonClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class AdminConfiguration {

    @Produces @ApplicationScoped
    public JsonClient jsonClient() {
        return new GsonJsonClient();
    }

    @Produces @ApplicationScoped
    public RestClient restClient(Environment environment, JsonClient jsonClient) {
        return new JavaRestClient(NumberUtils.toInt(environment.getProperty("http.connection.timeout")), jsonClient);
    }

    @Produces
    @ApplicationScoped
    public Environment environment() {
        return new StandardPropertyEnvironment();
    }

}
