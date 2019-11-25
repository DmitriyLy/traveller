package org.dmly.traveller.presentation.admin.metrics.healthcheck;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.dmly.traveller.app.infra.cdi.DBSource;
import org.dmly.traveller.app.infra.environment.Environment;
import org.dmly.traveller.app.monitoring.MetricsManager;
import org.dmly.traveller.app.monitoring.healthcheck.MySQLHealthCheck;
import org.dmly.traveller.app.persistence.repository.SystemRepository;
import org.dmly.traveller.presentation.admin.bean.startup.Eager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@Named
@ApplicationScoped
@Eager
@NoArgsConstructor
public class AdminMonitoringConfiguration {
    private static final String MYSQL_HEALTH_CHECK = "mysql";

    @Inject
    public AdminMonitoringConfiguration(MetricsManager metricsManager, @DBSource SystemRepository systemRepository, Environment environment) {
        metricsManager.registerHealthCheck(MYSQL_HEALTH_CHECK, new MySQLHealthCheck(systemRepository));

        boolean reportingEnabled = BooleanUtils.toBoolean(environment.getProperty("graphite.reporter.enabled"));
        if (reportingEnabled) {
            Graphite graphite = new Graphite(new InetSocketAddress(environment.getProperty("graphite.reporter.host"),
                    NumberUtils.toInt(environment.getProperty("graphite.reporter.port"))));

            final GraphiteReporter reporter = GraphiteReporter.forRegistry(metricsManager.getMetricRegistry())
                    .prefixedWith("admin")
                    .convertRatesTo(TimeUnit.SECONDS)
                    .convertDurationsTo(TimeUnit.MILLISECONDS)
                    .filter(MetricFilter.ALL)
                    .build(graphite);

            reporter.start(NumberUtils.toInt(environment.getProperty("graphite.reporter.duration")), TimeUnit.SECONDS);
        }
    }
}
