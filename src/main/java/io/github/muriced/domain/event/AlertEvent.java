package io.github.muriced.domain.event;

import java.time.LocalDateTime;
import java.util.Map;

import io.github.muriced.domain.enums.AlertSeverity;
import io.github.muriced.domain.enums.AlertType;

public record AlertEvent(
        String deviceId,
        AlertType type,
        AlertSeverity severity,
        Map<String, Object> context,
        LocalDateTime timestamp) {
}
