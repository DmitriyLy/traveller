package org.dmly.traveller.app.infra.util.generator;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class SequentialNumberGeneratorTest {

    @Test
    public void generate_limit9_numbersDontExceedTheLimit() {
        NumberGenerator numberGenerator = new SequentialNumberGenerator(9);

        IntStream.range(0, 1000).map(i -> numberGenerator.generate()).filter(i -> i > 9)
                .forEach(i -> fail("Generated number " + i + " exceeds limit 9"));
    }

    @Test
    public void generate_limit9_numbersAreSequential() {
        NumberGenerator numberGenerator = new SequentialNumberGenerator(5);

        int firstValue = numberGenerator.generate();
        assertEquals(numberGenerator.generate(), firstValue + 1);
    }
}