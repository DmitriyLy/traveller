package org.dmly.traveller.app.infra.environment;

import org.dmly.traveller.app.infra.environment.source.EnvironmentPropertySource;
import org.dmly.traveller.app.infra.environment.source.FilePropertySource;
import org.dmly.traveller.app.infra.environment.source.PropertySource;
import org.dmly.traveller.app.infra.environment.source.SystemPropertySource;
import org.dmly.traveller.app.infra.util.Checks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StandardPropertyEnvironment implements Environment {
    private static final String PROPERTIES_FILE = "application.properties";

    private final List<PropertySource> propertySources;

    public StandardPropertyEnvironment() {
        propertySources = new ArrayList<>();
        propertySources.add(new SystemPropertySource());
        propertySources.add(new FilePropertySource(PROPERTIES_FILE));
        propertySources.add(new EnvironmentPropertySource());
    }

    @Override
    public String getProperty(String name) {
        Checks.checkParameter(name != null, "Name should be not null");

        for (PropertySource source : propertySources) {
            String value = source.getProperty(name);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    @Override
    public Map<String, String> getProperties(String prefix) {
        Checks.checkParameter(prefix != null, "Prefix should be not null");

        Map<String, String> properties = new HashMap<>();
        for (PropertySource source : propertySources) {
            properties.putAll(
                    source.getProperties().entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith(prefix))
                        .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()))
            );
        }
        return properties;
    }
}
