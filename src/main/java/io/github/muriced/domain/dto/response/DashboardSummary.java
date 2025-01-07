package io.github.muriced.domain.dto.response;

import java.util.Map;

public record DashboardSummary(
    long totalDevices,
    long activeDevices,
    long alertCount,
    Map<String, Long> devicesByType,
    Map<String, Long> devicesByStatus
) {}
