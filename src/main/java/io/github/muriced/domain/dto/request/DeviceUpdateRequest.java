package io.github.muriced.domain.dto.request;

import io.github.muriced.domain.enums.DeviceStatus;

public record DeviceUpdateRequest(
    String name,
    String location,
    DeviceStatus status
) {}
