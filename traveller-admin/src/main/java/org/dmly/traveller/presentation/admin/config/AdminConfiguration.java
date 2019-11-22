package org.dmly.traveller.presentation.admin.config;

import org.dmly.traveller.app.infra.environment.Environment;
import org.dmly.traveller.app.infra.environment.StandardPropertyEnvironment;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class AdminConfiguration {

    @Produces
    @ApplicationScoped
    public Environment environment() {
        return new StandardPropertyEnvironment();
    }

}
