package org.dmly.traveller.app.monitoring;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;

import javax.inject.Named;

@Named
public class MetricsManager {

    private static final String DEFAULT_REGISTRY = "default";

    private static final HealthCheckRegistry HEALTH_CHECK_REGISTRY = new HealthCheckRegistry();

    private MetricRegistry metricRegistry;

    public MetricsManager() {
        metricRegistry = SharedMetricRegistries.getOrCreate(DEFAULT_REGISTRY);
        if (metricRegistry.getMetrics().isEmpty()) {
            metricRegistry.register("gc", new GarbageCollectorMetricSet());
            metricRegistry.register("threads", new ThreadStatesGaugeSet());
            metricRegistry.register("memory", new MemoryUsageGaugeSet());
        }
    }

    public MetricRegistry getMetricRegistry() {
        return metricRegistry;
    }

    public HealthCheckRegistry getHealthCheckRegistry() {
        return HEALTH_CHECK_REGISTRY;
    }

    public Metric registerMetric(final String name, final Metric metric) {
        return metricRegistry.register(name, metric);
    }

    public void registerHealthCheck(final String name, final HealthCheck healthCheck) {
        HEALTH_CHECK_REGISTRY.register(name, healthCheck);
        System.out.println("Names: " + HEALTH_CHECK_REGISTRY.getNames());
    }
}
