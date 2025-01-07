package io.github.muriced.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Map;

import io.github.muriced.domain.entities.Device;
import io.github.muriced.domain.entities.DeviceMetrics;
import io.github.muriced.domain.dto.request.DeviceRegistrationRequest;
import io.github.muriced.domain.dto.request.DeviceUpdateRequest;
import io.github.muriced.repository.DeviceMetricsRepository;
import io.github.muriced.repository.DeviceRepository;
import io.github.muriced.domain.enums.DeviceStatus;
import io.github.muriced.domain.exception.DeviceNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMetricsRepository deviceMetricsRepository;

    @Transactional
    public Device registerDevice(DeviceRegistrationRequest request) {
        Device device = new Device(
                null,
                request.name(),
                request.type(),
                request.location(),
                DeviceStatus.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now());
        return deviceRepository.save(device);
    }

    public Page<Device> getAllDevices(Pageable pageable) {
        return deviceRepository.findAll(pageable);
    }

    public Optional<Device> getDevice(String id) {
        return deviceRepository.findById(id);
    }

    @Transactional
    public Device updateDevice(String id, DeviceUpdateRequest request) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));

        Device updatedDevice = new Device(
                device.id(),
                request.name() != null ? request.name() : device.name(),
                device.type(),
                request.location() != null ? request.location() : device.location(),
                request.status() != null ? request.status() : device.status(),
                device.createdAt(),
                LocalDateTime.now());

        return deviceRepository.save(updatedDevice);
    }

    @Transactional
    public void deleteDevice(String id) {
        if (!deviceRepository.existsById(id)) {
            throw new DeviceNotFoundException(id);
        }
        deviceRepository.deleteById(id);
        log.info("Device deleted successfully: {}", id);
    }

    @Transactional
    public void updateDeviceMetrics(String deviceId, Map<String, Double> metrics) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException(deviceId));

        DeviceMetrics deviceMetrics = new DeviceMetrics(
                null,
                deviceId,
                metrics,
                LocalDateTime.now());

        deviceMetricsRepository.save(deviceMetrics);

        Device updatedDevice = new Device(
                device.id(),
                device.name(),
                device.type(),
                device.location(),
                device.status(),
                device.createdAt(),
                LocalDateTime.now());

        deviceRepository.save(updatedDevice);
        log.debug("Updated metrics for device {}: {}", deviceId, metrics);
    }

    @Transactional
    public void updateDeviceStatus(String deviceId, DeviceStatus newStatus) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException(deviceId));

        Device updatedDevice = new Device(
                device.id(),
                device.name(),
                device.type(),
                device.location(),
                newStatus,
                device.createdAt(),
                LocalDateTime.now());

        deviceRepository.save(updatedDevice);
        log.info("Updated status for device {} to {}", deviceId, newStatus);
    }
}