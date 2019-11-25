package org.dmly.traveller.app.monitoring.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dmly.traveller.app.persistence.repository.SystemRepository;

@RequiredArgsConstructor
@Slf4j
public class MySQLHealthCheck extends HealthCheck {

    private final SystemRepository systemRepository;

    @Override
    protected Result check() throws Exception {

        try {
            systemRepository.healthCheck();

            log.info("MYSQL healthcheck is successful");

            return HealthCheck.Result.healthy();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return HealthCheck.Result.unhealthy(e);
        }
    }
}
