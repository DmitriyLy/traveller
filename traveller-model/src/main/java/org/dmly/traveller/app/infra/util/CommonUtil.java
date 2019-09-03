package org.dmly.traveller.app.infra.util;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class CommonUtil {

    private CommonUtil() {
    }

    public static <T> Set<T> getSafeSet(Set<T> source) {
        return Collections.unmodifiableSet(Optional.ofNullable(source).orElse(Collections.emptySet()));
    }
}
