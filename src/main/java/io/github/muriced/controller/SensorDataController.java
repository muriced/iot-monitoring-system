package io.github.muriced.controller;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

import io.github.muriced.domain.dto.response.DeviceAnalytics;
import io.github.muriced.domain.entities.SensorReading;
import io.github.muriced.service.SensorDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/sensor-data")
@Tag(name = "Sensor Data", description = "API para dados dos sensores")
@RequiredArgsConstructor
public class SensorDataController {
    
    private final SensorDataService sensorDataService;
    
    @GetMapping("/device/{deviceId}")
    @Operation(summary = "Busca dados de um dispositivo específico")
    public ResponseEntity<List<SensorReading>> getDeviceReadings(
            @PathVariable String deviceId,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate) {
        return ResponseEntity.ok(sensorDataService.getDeviceReadings(deviceId, startDate, endDate));
    }
    
    @GetMapping("/analytics")
    @Operation(summary = "Obtém análises dos dados dos sensores")
    public ResponseEntity<DeviceAnalytics> getAnalytics(
            @RequestParam(required = false) String deviceType,
            @RequestParam(required = false) String location) {
        return ResponseEntity.ok(sensorDataService.getAnalytics(deviceType, location));
    }
}
