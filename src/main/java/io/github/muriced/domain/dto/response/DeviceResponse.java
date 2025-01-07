package io.github.muriced.domain.dto.response;

import java.time.LocalDateTime;

import io.github.muriced.domain.enums.DeviceStatus;

public record DeviceResponse(
    String id,
    String name,
    String type,
    String location,
    DeviceStatus status,
    LocalDateTime lastReading
) {}
