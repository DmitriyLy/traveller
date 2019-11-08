package org.dmly.traveller.app.infra.util.generator;

public class RandomNumberGenerator implements NumberGenerator {

    private final int limit;

    public RandomNumberGenerator(int limit) {
        this.limit = limit;
    }

    @Override
    public int generate() {
        return (int) (Math.random() * limit);
    }
}
