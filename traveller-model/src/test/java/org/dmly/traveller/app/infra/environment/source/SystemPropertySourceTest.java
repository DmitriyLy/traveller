package org.dmly.traveller.app.infra.environment.source;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class SystemPropertySourceTest {
    private static final String PROP_TEST = "test";

    private PropertySource propertySource;

    @Before
    public void setup() {
        System.setProperty(PROP_TEST, PROP_TEST);
        propertySource = new SystemPropertySource();
    }

    @Test
    public void getProperty_nonExistingProperty_nullReturned() {
        assertNull(propertySource.getProperty("some_property"));
    }

    @Test
    public void getProperty_existingProperty_valueReturned() {
        assertEquals(PROP_TEST, propertySource.getProperty(PROP_TEST));
    }

    @Test
    public void getProperties_multipleEntites_multipleEntriesReturned() {
        Map<String, String> properties = propertySource.getProperties();
        assertTrue(properties.size() >= 1);
        assertEquals(PROP_TEST, properties.get(PROP_TEST));
    }
}