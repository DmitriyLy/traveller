package org.dmly.traveller.app.infra.environment.source;

import java.util.Map;

public interface PropertySource {

    String getProperty(String name);

    Map<String, String> getProperties();

}
