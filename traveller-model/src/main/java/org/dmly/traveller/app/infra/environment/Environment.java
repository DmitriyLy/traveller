package org.dmly.traveller.app.infra.environment;

import java.util.Map;
import java.util.Optional;

public interface Environment {

    String getProperty(String name);

    default String getProperty(String name, String defaultValue) {
        return Optional.ofNullable(getProperty(name)).orElse(defaultValue);
    }

    Map<String, String> getProperties(String prefix);

}
