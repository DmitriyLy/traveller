package org.dmly.traveller.presentation.admin.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;
import org.dmly.traveller.app.monitoring.MetricsManager;

import javax.inject.Inject;
import javax.servlet.annotation.WebListener;

@WebListener
public class AdminMetricsServletContextListener extends MetricsServlet.ContextListener {

    @Inject
    private MetricsManager metricsManager;

    @Override
    protected MetricRegistry getMetricRegistry() {
        return metricsManager.getMetricRegistry();
    }
}
