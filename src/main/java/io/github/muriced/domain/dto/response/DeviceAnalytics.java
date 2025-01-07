package io.github.muriced.domain.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

public record DeviceAnalytics(
    String deviceId,
    Map<String, Double> averageReadings,
    Map<String, Double> maxReadings,
    Map<String, Double> minReadings,
    int totalReadings,
    Double averageReading,
    String location,
    LocalDateTime lastReading
) {}
