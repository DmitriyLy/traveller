package org.dmly.traveller.app.infra.util.generator;

import org.junit.Test;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class SecureRandomNumberGeneratorTest {

    @Test
    public void generate_limit9_numbersDontExceedTheLimit() {
        NumberGenerator numberGenerator = new SecureRandomNumberGenerator(9);

        IntStream.range(0, 1000).map(i -> numberGenerator.generate()).filter(i -> i > 9)
                .forEach(i -> fail("Generated number " + i + " exceeds limit 9"));
    }

    @Test
    public void generate_limit9_numbersAreRandomAndDistributionDoesntExceedDelta() {
        int limit = 20;
        int number = 1000;
        int delta = 50;
        NumberGenerator numberGenerator = new SecureRandomNumberGenerator(limit);

        Map<Integer, Long> results = IntStream.range(0, number).map(i -> numberGenerator.generate()).boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        assertEquals(limit, results.size());
        int avg = number / limit;
        results.values().stream().filter(i -> Math.abs(i - avg) * 100 / avg > delta)
                .forEach(res -> fail("Random distribution fails with value " + res));
    }
}