package org.dmly.traveller.app.infra.environment;

import org.dmly.traveller.app.infra.exception.flow.InvalidParameterException;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class StandardPropertyEnvironmentTest {
    private Environment environment;

    @Before
    public void setup() {
        System.setProperty("key", "value2");
        environment = new StandardPropertyEnvironment();
    }

    @Test
    public void getProperty_nonExistingProperty_nullReturned() {
        assertNull(environment.getProperty("test111"));
    }

    @Test
    public void getProperty_existingProperty_valueReturned() {
        assertEquals("value2", environment.getProperty("key"));
    }

    @Test
    public void getProperties_existingPrefix_twoEntriesReturned() {
        Map<String, String> properties = environment.getProperties("hibernate");
        assertEquals(2, properties.size());
        assertEquals("1", properties.get("hibernate.key1"));
        assertEquals("2", properties.get("hibernate.key2"));
    }

    @Test
    public void getProperties_nonexistingPrefix_noEntriesReturned() {
        Map<String, String> properties = environment.getProperties("spring");
        assertTrue(properties.isEmpty());
    }

    @Test(expected=InvalidParameterException.class)
    public void getProperties_nullPrefix_exceptionThrown() {
        environment.getProperties(null);
    }

}