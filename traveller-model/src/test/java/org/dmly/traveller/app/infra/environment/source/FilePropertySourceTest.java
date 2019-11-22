package org.dmly.traveller.app.infra.environment.source;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class FilePropertySourceTest {
    private PropertySource propertySource;

    @Before
    public void setup() {
        propertySource = new FilePropertySource("test.properties");
    }

    @Test
    public void getProperty_nonExistingProperty_nullReturned() {
        assertNull(propertySource.getProperty("test"));
    }

    @Test
    public void getProperty_existingProperty_valueReturned() {
        assertEquals("1", propertySource.getProperty("prop"));
    }

    @Test
    public void getProperties_twoEntries_twoEntriesReturned() {
        Map<String, String> properties = propertySource.getProperties();
        assertEquals(2, properties.size());
        assertEquals("1", properties.get("prop"));
        assertEquals("2", properties.get("prop2"));
    }

}