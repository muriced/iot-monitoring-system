package io.github.muriced.domain.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

public record DashboardMetrics(
    LocalDateTime timestamp,
    Map<String, Double> metrics,
    int activeAlerts,
    int activeDevices
) {}
