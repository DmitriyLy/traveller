package org.dmly.traveller.app.infra.util.generator.text;

import org.dmly.traveller.app.infra.util.Checks;
import org.dmly.traveller.app.infra.util.generator.NumberGenerator;
import org.dmly.traveller.app.infra.util.generator.RandomNumberGenerator;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomDigitStringGenerator implements StringGenerator {

    private final int size;

    private final NumberGenerator numberGenerator;

    public RandomDigitStringGenerator(int size) {
        Checks.checkParameter(size > 0, "String length should be greater than zero");
        this.size = size;
        this.numberGenerator = new RandomNumberGenerator(10);
    }

    @Override
    public String generate() {
        return IntStream.range(0, size)
                .map(i -> numberGenerator.generate())
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
