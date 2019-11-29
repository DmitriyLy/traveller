package org.dmly.traveller.app.infra.environment.source;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.dmly.traveller.common.infra.util.Checks;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class FilePropertySource implements PropertySource {

    private final ImmutableMap<String, String> properties;
    private final String fileName;

    public FilePropertySource(final String fileName) {
        this.fileName = Objects.requireNonNull(fileName);
        properties = Maps.fromProperties(loadProperties());
    }


    @Override
    public String getProperty(String name) {
        Checks.checkParameter(name != null, "Name should be null");

        return properties.get(name);
    }

    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

    private Properties loadProperties() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            Properties properties = new Properties();

            properties.load(inputStream);

            return properties;
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }
}
