package org.dmly.traveller.app.infra.util.generator;

import java.security.SecureRandom;
import java.util.Random;

public class SecureRandomNumberGenerator implements NumberGenerator {

    private final Random random = new SecureRandom();

    private final int limit;

    public SecureRandomNumberGenerator(int limit) {
        this.limit = limit;
    }

    @Override
    public int generate() {
        return random.nextInt(limit);
    }
}
