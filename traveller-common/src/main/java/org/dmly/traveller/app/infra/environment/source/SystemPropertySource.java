package org.dmly.traveller.app.infra.environment.source;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.dmly.traveller.common.infra.util.Checks;

import java.util.Map;

@Slf4j
public class SystemPropertySource implements PropertySource {

    @Override
    public String getProperty(final String name) {
        Checks.checkParameter(name != null, "Name should be not null");
        try {
            return System.getProperty(name);
        } catch (SecurityException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Map<String, String> getProperties() {
        try {
            return Maps.fromProperties(System.getProperties());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Map.of();
        }
    }
}
