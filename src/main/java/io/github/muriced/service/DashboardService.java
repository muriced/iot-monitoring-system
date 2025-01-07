package io.github.muriced.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import io.github.muriced.domain.dto.response.DashboardSummary;
import io.github.muriced.domain.dto.response.DashboardMetrics;
import io.github.muriced.domain.entities.Device;
import io.github.muriced.domain.enums.AlertStatus;
import io.github.muriced.domain.enums.DeviceStatus;
import io.github.muriced.repository.DeviceRepository;
import io.github.muriced.repository.AlertRepository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DeviceRepository deviceRepository;
    private final AlertRepository alertRepository;

    public DashboardSummary getDashboardSummary() {
        long totalDevices = deviceRepository.count();
        long activeDevices = deviceRepository.findByStatus(DeviceStatus.ACTIVE).size();
        long alertCount = alertRepository.findActiveCriticalAlerts().size();

        Map<String, Long> devicesByType = deviceRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Device::type,
                        Collectors.counting()));

        Map<String, Long> devicesByStatus = deviceRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        device -> device.status().toString(),
                        Collectors.counting()));

        return new DashboardSummary(
                totalDevices,
                activeDevices,
                alertCount,
                devicesByType,
                devicesByStatus);
    }

    public Flux<DashboardMetrics> streamMetrics() {
        return Flux.interval(java.time.Duration.ofSeconds(1))
                .map(tick -> new DashboardMetrics(
                        LocalDateTime.now(),
                        getCurrentMetrics(),
                        getActiveAlertCount(),
                        getActiveDeviceCount()));
    }

    private Map<String, Double> getCurrentMetrics() {
        return Map.of(
                "cpu_usage", 45.5,
                "memory_usage", 78.3,
                "network_throughput", 256.7);
    }

    private int getActiveAlertCount() {
        return alertRepository.findByStatus(AlertStatus.ACTIVE).size();
    }

    private int getActiveDeviceCount() {
        return deviceRepository.findByStatus(DeviceStatus.ACTIVE).size();
    }
}