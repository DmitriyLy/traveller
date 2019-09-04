package org.dmly.traveller.app.infra.util;

import org.dmly.traveller.app.infra.exception.flow.InvalidParameterException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChecksTest {

    @Test
    public void testCheckParameterGetException() {
        try {
            Checks.checkParameter(false, "Test message");
        } catch (Exception e) {
            assertSame(InvalidParameterException.class, e.getClass());
            assertEquals("Test message", e.getMessage());
        }
    }

    @Test
    public void testCheckParameterNoException() {
        Checks.checkParameter(true, "test");
        assertTrue(true);
    }
}