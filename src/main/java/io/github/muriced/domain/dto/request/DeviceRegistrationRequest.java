package io.github.muriced.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DeviceRegistrationRequest(
    @NotBlank String name,
    @NotBlank String type,
    @NotBlank String location
) {}
