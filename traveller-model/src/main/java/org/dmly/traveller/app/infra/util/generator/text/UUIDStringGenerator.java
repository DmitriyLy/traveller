package org.dmly.traveller.app.infra.util.generator.text;

import java.util.UUID;

public class UUIDStringGenerator implements StringGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
