package org.dmly.traveller.presentation.admin.metrics.healthcheck;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricFilter;
import lombok.NoArgsConstructor;
import org.dmly.traveller.app.monitoring.MetricsManager;
import org.dmly.traveller.app.monitoring.healthcheck.MySQLHealthCheck;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.presentation.admin.bean.startup.Eager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;

@Named
@ApplicationScoped
@Eager
@NoArgsConstructor
public class AdminMonitoringConfiguration {
    private static final String MYSQL_HEALTH_CHECK = "mysql";

    @Inject
    public AdminMonitoringConfiguration(MetricsManager metricsManager, SessionFactoryBuilder sessionFactoryBuilder) {
        metricsManager.registerHealthCheck(MYSQL_HEALTH_CHECK, new MySQLHealthCheck(sessionFactoryBuilder));

        ConsoleReporter reporter = ConsoleReporter.forRegistry(metricsManager.getMetricRegistry())
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MICROSECONDS)
                .filter(MetricFilter.ALL)
                .build();

        reporter.start(30, TimeUnit.SECONDS);
    }
}
