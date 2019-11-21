package org.dmly.traveller.presentation.admin.metrics;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;
import org.dmly.traveller.app.monitoring.MetricsManager;

import javax.inject.Inject;
import javax.servlet.annotation.WebListener;

@WebListener
public class AdminHealthCheckServletContextListener extends HealthCheckServlet.ContextListener {

    @Inject
    public MetricsManager metricsManager;

    @Override
    protected HealthCheckRegistry getHealthCheckRegistry() {
        return metricsManager.getHealthCheckRegistry();
    }
}
