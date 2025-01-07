package io.github.muriced.domain.valueObjects;

public record MetricThreshold(
        String metricName,
        double minValue,
        double maxValue,
        double warningThreshold,
        double criticalThreshold) {
}
