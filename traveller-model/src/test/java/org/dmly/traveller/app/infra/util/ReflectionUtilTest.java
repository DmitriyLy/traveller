package org.dmly.traveller.app.infra.util;

import org.dmly.traveller.app.infra.exception.ConfigurationException;
import org.dmly.traveller.app.infra.exception.flow.InvalidParameterException;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ReflectionUtilTest {

    @Test
    public void testCreateInstanceSuccess() {
        Object value = ReflectionUtil.createInstance(Source.class);
        assertNotNull(value);
    }

    @Test(expected = ConfigurationException.class)
    public void testCreateInstanceFailure() {
        ReflectionUtil.createInstance(Restricted.class);
        assertTrue(false);
    }

    @Test
    public void testFindSimilarFieldsSuccess() {
        List<String> fields = ReflectionUtil.findSimilarField(Source.class, Destination.class);

        assertNotNull(fields);
        assertTrue(fields.contains("value"));
    }

    @Test
    public void testCopyFieldsSuccess() {
        Source source = new Source();
        source.setValue(10);
        Destination destination = new Destination();
        List<String> fields = ReflectionUtil.findSimilarField(source.getClass(), destination.getClass());
        ReflectionUtil.copyFields(source, destination, fields);

        assertEquals(10, destination.getValue());
    }

    @Test(expected = InvalidParameterException.class)
    public void copyFieldsDestinationNullFailure() {
        Source src = new Source();
        ReflectionUtil.copyFields(src, null, Collections.emptyList());
    }

}

class Source {
    private int value;
    private String text;

    public void setValue(int value) {
        this.value = value;
    }
}

class Destination {
    private int value;

    public int getValue() {
        return value;
    }
}

class Restricted {
    public Restricted(int value) {

    }
}