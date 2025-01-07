package io.github.muriced.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

import io.github.muriced.domain.enums.AlertStatus;
import io.github.muriced.domain.enums.AlertType;
import io.github.muriced.domain.enums.AlertSeverity;

@Document(collection = "alerts")
public record Alert(
    @Id
    String id,
    String deviceId,
    AlertType type,
    AlertSeverity severity,
    String message,
    LocalDateTime createdAt,
    LocalDateTime acknowledgedAt,
    String acknowledgedBy,
    AlertStatus status
) {}