package org.dmly.traveller.app.infra.util;

import org.dmly.traveller.app.infra.exception.flow.InvalidParameterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChecksTest {

    @Test
    @DisplayName("Checks that checkParameter throws exception if condition is false")
    void testCheckParameterGetException() {
        Exception exception = assertThrows(InvalidParameterException.class, () -> Checks.checkParameter(false, "test"));

        assertEquals("test", exception.getMessage(), "Exception message is not test");
    }

    @Test
    @DisplayName("Checks that checkParameter doesn't throw exception if condition is true")
    void testCheckParameterNoException() {
        Checks.checkParameter(true, "test");
        assertTrue(true);
    }
}