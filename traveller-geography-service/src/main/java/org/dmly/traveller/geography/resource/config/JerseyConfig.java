package org.dmly.traveller.geography.resource.config;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.dmly.traveller.geography.binding.ComponentFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        super(ComponentFeature.class);
        packages("org.dmly.traveller.geography.resource");

        initBeanConfig();

        register(ApiListingResource.class);
        register(SwaggerSerializers.class);
    }

    private void initBeanConfig() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[] { "http" });
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("org.dmly.traveller.geography.resource");
        beanConfig.setScan(true);
    }
}
