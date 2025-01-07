package io.github.muriced.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Map;

import io.github.muriced.domain.enums.ReadingStatus;

@Document(collection = "sensor_readings")
public record SensorReading(
    @Id String id,
    String deviceId,
    Map<String, Double> metrics,
    Map<String, String> metadata,
    LocalDateTime timestamp,
    ReadingStatus status
) {}
