package io.github.muriced.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import io.github.muriced.domain.event.DeviceEvent;
import io.github.muriced.domain.event.AlertEvent;
import io.github.muriced.domain.enums.DeviceStatus;
import io.github.muriced.domain.enums.AlertType;
import io.github.muriced.domain.enums.AlertSeverity;
import io.github.muriced.service.DeviceService;
import io.github.muriced.service.AlertService;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceEventConsumer {
    private final DeviceService deviceService;
    private final AlertService alertService;

    @KafkaListener(topics = "device-events", groupId = "device-monitoring-group", containerFactory = "deviceEventKafkaListenerContainerFactory")
    public void consume(
            @Payload DeviceEvent event,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp) {
        try {
            log.info("Received device event: {} from partition: {}", event.deviceId(), partition);

            switch (event.type()) {
                case READING -> processDeviceReading(event);
                case STATUS_CHANGE -> processStatusChange(event);
                case ERROR -> processError(event);
                default -> log.warn("Unknown event type: {}", event.type());
            }
        } catch (Exception e) {
            log.error("Error processing device event: {}", e.getMessage(), e);
        }
    }

    private void processDeviceReading(DeviceEvent event) {
        Map<String, Object> data = event.data() instanceof Map ? (Map<String, Object>) event.data() : Map.of();

        Map<String, Double> metrics = data.entrySet().stream()
                .filter(entry -> entry.getValue() instanceof Double)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> (Double) entry.getValue()));

        deviceService.updateDeviceMetrics(event.deviceId(), metrics);

        if (needsAlert(metrics)) {
            AlertEvent alert = createAlertFromMetrics(event, metrics);
            alertService.createAlert(alert);
        }
    }

    private void processStatusChange(DeviceEvent event) {
        Object status = event.data().get("status");
        DeviceStatus newStatus = (status instanceof DeviceStatus) ? (DeviceStatus) status : DeviceStatus.ACTIVE;
        deviceService.updateDeviceStatus(event.deviceId(), newStatus);
    }

    private void processError(DeviceEvent event) {
        AlertEvent alert = new AlertEvent(
                event.deviceId(),
                AlertType.SYSTEM_ERROR,
                AlertSeverity.HIGH,
                event.data(),
                LocalDateTime.now());
        alertService.createAlert(alert);
    }

    private boolean needsAlert(Map<String, Double> metrics) {
        return metrics.values().stream()
                .anyMatch(value -> value > 100 || value < 0);
    }

    private AlertEvent createAlertFromMetrics(DeviceEvent event, Map<String, Double> metrics) {
        return new AlertEvent(
                event.deviceId(),
                AlertType.METRIC_THRESHOLD,
                AlertSeverity.WARNING,
                Map.of(
                        "metrics", metrics,
                        "message", "Valores de métricas anormais detectados"),
                LocalDateTime.now());
    }
}
