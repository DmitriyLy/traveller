package org.dmly.traveller.app.infra.environment;

import org.dmly.traveller.app.infra.util.Checks;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

public class StandardPropertyEnvironment implements Environment {
    private static final String PROPERTIES_FILE = "application.properties";

    private final Properties properties;

    public StandardPropertyEnvironment() {
        properties = loadProperties();
    }

    @Override
    public String getProperty(String name) {
        Checks.checkParameter(name != null, "Name should be not null");

        return properties.getProperty(name);
    }

    @Override
    public Map<String, String> getProperties(String prefix) {
        Checks.checkParameter(prefix != null, "Prefix should be not null");

        return properties.keySet().stream()
                .map(Object::toString)
                .filter(item -> item.startsWith(prefix))
                .collect(Collectors.toMap(key -> key, key -> properties.getProperty(key)));
    }

    private Properties loadProperties() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
            Properties properties = new Properties();

            properties.load(inputStream);

            return properties;
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }
}
