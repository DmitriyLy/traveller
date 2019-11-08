package org.dmly.traveller.app.infra.util.generator.text;

import org.junit.Test;

import static org.junit.Assert.*;

public class UUIDStringGeneratorTest {
    @Test
    public void generate_validUUIDgeneratedContainingOnlyHexCharacters() {
        int length = 36;
        StringGenerator generator = new UUIDStringGenerator();

        String text = generator.generate();

        assertEquals(length, text.length());
        assertEquals(length - 4,
                text.replace("-", "")
                        .chars()
                        .map(ch -> Integer.parseInt(String.valueOf(ch), 16))
                        .count());
    }
}