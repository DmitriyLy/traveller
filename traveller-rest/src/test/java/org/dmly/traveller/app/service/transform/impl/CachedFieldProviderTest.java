package org.dmly.traveller.app.service.transform.impl;

import org.dmly.traveller.app.infra.util.ReflectionUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ReflectionUtil.class)
public class CachedFieldProviderTest {
    private FieldProvider provider;

    @Before
    public void setUp() throws Exception {
        provider = new CachedFieldProvider();
    }

    @Test
    public void testGetFieldNamesSuccess() {
        List<String> fields = provider.getFieldNames(Source.class, Destination.class);
        assertFalse(fields.isEmpty());
        assertTrue(fields.contains("value"));
    }

    @Test
    public void testGetFieldNamesCachedSuccess() {
        List<String> fields = provider.getFieldNames(Source.class, Destination.class);
        List<String> fields2 = provider.getFieldNames(Source.class, Destination.class);

        assertFalse(fields.isEmpty());
        assertFalse(fields2.isEmpty());
        assertEquals(fields, fields2);
    }

    @Test
    public void testGetFieldNamesCached() {
        List<String> fields = provider.getFieldNames(Source.class, Destination.class);

        PowerMockito.mockStatic(ReflectionUtil.class);
        when(ReflectionUtil.findSimilarField(Source.class, Destination.class)).thenReturn(Collections.emptyList());

        assertTrue(ReflectionUtil.findSimilarField(Source.class, Destination.class).isEmpty());

        List<String> fields2 = provider.getFieldNames(Source.class, Destination.class);

        assertFalse(fields.isEmpty());
        assertFalse(fields2.isEmpty());
        assertEquals(fields, fields2);
    }
}

class Source {
    int value;
}

class Destination {
    int value;
}