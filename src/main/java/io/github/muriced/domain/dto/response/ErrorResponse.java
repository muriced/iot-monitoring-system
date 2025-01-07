package io.github.muriced.domain.dto.response;

public record ErrorResponse(
    String code,
    String message
) {}
