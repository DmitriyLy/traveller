package org.dmly.traveller.app.service.transform.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FieldProviderTest {

    private FieldProvider fieldProvider;

    @Before
    public void setup() {
        fieldProvider = new FieldProvider();
    }

    @Test
    public void testGetFieldNames() {
        List<String> fields = fieldProvider.getFieldNames(SourceFieldProvider.class, DestinationFieldProvider.class);

        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertTrue(fields.contains("value"));
    }

}

class SourceFieldProvider {
    private int value;
}

class DestinationFieldProvider {
    private int value;
}