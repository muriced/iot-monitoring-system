package io.github.muriced.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "device_metrics")
public record DeviceMetrics(
    @Id
    String id,
    String deviceId,
    Map<String, Double> metrics,
    LocalDateTime timestamp
) {}
