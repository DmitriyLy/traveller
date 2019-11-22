package org.dmly.traveller.app.infra.environment;

import java.util.Map;

public interface Environment {

    String getProperty(String name);

    Map<String, String> getProperties(String prefix);
}
