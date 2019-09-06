package org.dmly.traveller.app.rest.service.config;

import org.dmly.traveller.app.config.ComponentFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        super(ComponentFeature.class);
        packages("org.dmly.traveller.app.rest");
    }
}
