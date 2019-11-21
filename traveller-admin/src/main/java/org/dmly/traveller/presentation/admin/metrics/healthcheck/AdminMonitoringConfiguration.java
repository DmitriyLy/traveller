package org.dmly.traveller.presentation.admin.metrics.healthcheck;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import lombok.NoArgsConstructor;
import org.dmly.traveller.app.monitoring.MetricsManager;
import org.dmly.traveller.app.monitoring.healthcheck.MySQLHealthCheck;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
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
    public AdminMonitoringConfiguration(MetricsManager metricsManager, SessionFactoryBuilder sessionFactoryBuilder) {
        metricsManager.registerHealthCheck(MYSQL_HEALTH_CHECK, new MySQLHealthCheck(sessionFactoryBuilder));

        Graphite graphite = new Graphite(new InetSocketAddress("graphite", 2003));
        final GraphiteReporter reporter = GraphiteReporter.forRegistry(metricsManager.getMetricRegistry())
                .prefixedWith("admin").convertRatesTo(TimeUnit.SECONDS)
                .convertRatesTo(TimeUnit.MICROSECONDS)
                .filter(MetricFilter.ALL)
                .build(graphite);

        reporter.start(30, TimeUnit.SECONDS);
    }
}
