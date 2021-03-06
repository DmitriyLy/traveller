package org.dmly.traveller.presentation.admin.metrics.healthcheck;

import lombok.NoArgsConstructor;
import org.dmly.traveller.app.infra.cdi.DBSource;
import org.dmly.traveller.app.monitoring.MetricsManager;
import org.dmly.traveller.app.monitoring.healthcheck.MySQLHealthCheck;
import org.dmly.traveller.app.persistence.repository.SystemRepository;
import org.dmly.traveller.presentation.admin.bean.startup.Eager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
@Eager
@NoArgsConstructor
public class AdminHealthCheckConfiguration {
    private static final String MYSQL_HEALTH_CHECK = "mysql";

    @Inject
    public AdminHealthCheckConfiguration(MetricsManager metricsManager, @DBSource SystemRepository systemRepository) {
        metricsManager.registerHealthCheck(MYSQL_HEALTH_CHECK, new MySQLHealthCheck(systemRepository));
    }
}
