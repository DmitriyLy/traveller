package org.dmly.traveller.app.infra.environment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StandardPropertyEnvironmentTest {

    private Environment environment;

    @Before
    public void setup() {
        environment = new StandardPropertyEnvironment();
    }

    @Test
    public void getProperty_nonExistingProperty_nullReturned() {
        assertNull(environment.getProperty("test"));
    }

    @Test
    public void getProperty_existingProperty_valueReturned() {
        assertEquals("value", environment.getProperty("key"));
    }
}