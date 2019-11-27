package org.dmly.traveller.app.infra.environment;

import org.dmly.traveller.app.infra.exception.flow.InvalidParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StandardPropertyEnvironmentTest {
    private Environment environment;

    @BeforeEach
    void setup() {
        System.setProperty("key", "value2");
        environment = new StandardPropertyEnvironment();
    }

    @Test
    void getProperty_nonExistingProperty_nullReturned() {
        assertNull(environment.getProperty("test111"));
    }

    @Test
    void getProperty_nonExistingProperty_defaultValueReturned() {
        assertEquals(environment.getProperty("test111", "1"), "1");
    }

    @Test
    void getProperty_existingProperty_valueReturned() {
        assertEquals("value2", environment.getProperty("key"));
    }

    @Test
    void getProperties_existingPrefix_twoEntriesReturned() {
        Map<String, String> properties = environment.getProperties("hibernate");
        assertEquals(2, properties.size());
        assertEquals("1", properties.get("hibernate.key1"));
        assertEquals("2", properties.get("hibernate.key2"));
    }

    @Test
    void getProperties_nonexistingPrefix_noEntriesReturned() {
        Map<String, String> properties = environment.getProperties("spring");
        assertTrue(properties.isEmpty());
    }

    @Test
    void getProperties_nullPrefix_exceptionThrown() {
        assertThrows(InvalidParameterException.class, () -> environment.getProperties(null));
    }

}