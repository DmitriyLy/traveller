package org.dmly.traveller.common.infra.util;

import org.dmly.traveller.app.infra.exception.flow.InvalidParameterException;

public class Checks {
    private Checks() {
    }

    public static void checkParameter(final boolean check, final String message) throws InvalidParameterException {
        if (!check) {
            throw new InvalidParameterException(message);
        }
    }
}
