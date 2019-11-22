package org.dmly.traveller.app.infra.environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.PersistenceException;

public class StandardPropertyEnvironment implements Environment {
    private static final String PROPERTIES_FILE = "application.properties";

    private final Properties properties;

    public StandardPropertyEnvironment() {
        properties = loadProperties();
    }

    @Override
    public String getProperty(String name) {
        return properties.getProperty(name);
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
