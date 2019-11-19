package org.dmly.traveller.app.infra.util;

import org.dmly.traveller.app.infra.exception.ConfigurationException;
import org.dmly.traveller.app.infra.exception.flow.InvalidParameterException;
import org.dmly.traveller.app.infra.util.annotation.Ignore;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ReflectionUtilTest {

    @Test
    public void createInstanceSuccess() {
        Object value = ReflectionUtil.createInstance(Source.class);
        assertNotNull(value);
    }

    @Test(expected = ConfigurationException.class)
    public void testCreateInstanceFailure() {
        ReflectionUtil.createInstance(Restricted.class);
    }

    @Test
    public void testFindSimilarFieldsSuccess() {
        List<String> fields = ReflectionUtil.findSimilarFields(Source.class,
                Destination.class);
        assertNotNull(fields);
        assertTrue(fields.contains("value"));
    }

    @Test
    public void testCopyFieldsSuccess() {
        Source src = new Source();
        src.setValue(10);
        Destination dest = new Destination();
        List<String> fields = ReflectionUtil.findSimilarFields(src.getClass(), dest.getClass());

        ReflectionUtil.copyFields(src, dest, fields);
        assertEquals(dest.getValue(), 10);
    }

    @Test
    public void copyFindSimilarFieldsWithIgnoreSuccess() {
        List<String> fields = ReflectionUtil.findSimilarFields(Source.class, Destination.class);
        assertFalse(fields.contains("ignored"));
    }

    @Test
    public void copyFindSimilarFieldsForStaticAndFinalSuccess() {
        List<String> fields = ReflectionUtil.findSimilarFields(Source.class, Destination.class);
        assertFalse(fields.contains("staticField"));
        assertFalse(fields.contains("finalField"));
    }

    @Test
    public void copyFindSimilarFieldsForBaseFieldSuccess() {
        List<String> fields = ReflectionUtil.findSimilarFields(Source.class, Destination.class);
        assertTrue(fields.contains("baseField"));
    }

    @Test(expected=InvalidParameterException.class)
    public void copyFieldsDestinationNullFailure() {
        Source src = new Source();
        ReflectionUtil.copyFields(src, null, Collections.emptyList());
    }

    @Test
    public void getFieldsWithFilters_IgnoreFilter_1fieldReturned() {
        List<String> fields = ReflectionUtil.getFields(Source.class, List.of(field -> field.isAnnotationPresent(Ignore.class)));
        assertEquals(1, fields.size());
    }

    @Test
    public void getFieldsWithFilters_NoFilters_allFieldsReturned() {
        List<String> fields = ReflectionUtil.getFields(Source.class, List.of());
        assertEquals(6, fields.size());
    }

    @Test
    public void getFieldValue_validFieldName_valueReturned() {
        Source source = new Source();
        int value = (int) ReflectionUtil.getFieldValue(source, "ignored");
        assertEquals(2, value);
    }

    @Test(expected=ConfigurationException.class)
    public void getFieldValue_invvalidFieldName_exceptionThrown() {
        Source source = new Source();
        ReflectionUtil.getFieldValue(source, "invalidname");
    }

    @Test
    public void setFieldValue_validFieldNameAndValue_valueChanged() {
        Source source = new Source();
        int newValue = 10;
        ReflectionUtil.setFieldValue(source, "ignored", newValue);
        int value = (int) ReflectionUtil.getFieldValue(source, "ignored");
        assertEquals(newValue, value);
    }

}

class BaseSource {
    private int baseField;
}

class BaseDestination {
    private int baseField;
}

class Source extends BaseSource {
    private int value;

    private String text;

    @Ignore
    private int ignored = 2;

    private static int staticField;

    private final int finalField = 0;

    public void setValue(int value) {
        this.value = value;
    }
}

class Destination extends BaseDestination {
    private int value;

    private int ignored;

    private int staticField;

    private int finalField = 0;

    public int getValue() {
        return value;
    }
}

class Restricted {
    public Restricted(int value) {

    }
}