package org.dmly.traveller.app.infra.util.generator.text;

import org.junit.Test;

import static org.junit.Assert.*;

public class RandomDigitStringGeneratorTest {
    @Test
    public void generate_size10_stringWith10digitsGenerated() {
        int length = 10;
        StringGenerator generator = new RandomDigitStringGenerator(length);

        String text = generator.generate();

        assertEquals(text.length(), length);
        assertTrue(text.chars().allMatch(Character::isDigit));
    }
}