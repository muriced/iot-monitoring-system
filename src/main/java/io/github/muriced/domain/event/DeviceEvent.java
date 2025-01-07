package io.github.muriced.domain.event;

import java.time.LocalDateTime;
import java.util.Map;

import io.github.muriced.domain.enums.DeviceEventType;

public record DeviceEvent(
        String deviceId,
        DeviceEventType type,
        Map<String, Object> data,
        LocalDateTime timestamp) {
}
