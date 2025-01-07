package io.github.muriced.domain.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import io.github.muriced.domain.enums.DeviceStatus;

@Document(collection = "devices")
public record Device(
    @Id String id,
    String name,
    String type,
    String location,
    DeviceStatus status,
    @CreatedDate LocalDateTime createdAt,
    @LastModifiedDate LocalDateTime updatedAt
) {}
