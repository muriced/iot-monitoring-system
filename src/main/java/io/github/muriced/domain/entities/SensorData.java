package io.github.muriced.domain.entities;

import java.time.LocalDateTime;
import java.util.Map;

public record SensorData(
    String deviceId,
    Map<String, Double> metrics,
    LocalDateTime timestamp
) {}
