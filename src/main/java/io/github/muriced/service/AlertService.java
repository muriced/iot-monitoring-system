package io.github.muriced.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import io.github.muriced.domain.entities.Alert;
import io.github.muriced.domain.event.AlertEvent;
import io.github.muriced.domain.enums.AlertSeverity;
import io.github.muriced.domain.enums.AlertStatus;
import io.github.muriced.domain.enums.AlertType;
import io.github.muriced.domain.enums.ReadingStatus;
import io.github.muriced.repository.AlertRepository;
import io.github.muriced.kafka.producer.AlertEventProducer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertService {

    private final AlertRepository alertRepository;
    private final AlertEventProducer alertEventProducer;

    @Transactional
    public Alert createAlert(AlertEvent event) {
        Alert alert = new Alert(
                null,
                event.deviceId(),
                event.type(),
                event.severity(),
                event.context().get("message").toString(),
                LocalDateTime.now(),
                null,
                null,
                AlertStatus.ACTIVE);

        Alert savedAlert = alertRepository.save(alert);
        alertEventProducer.sendAlertEvent(event);
        return savedAlert;
    }

    public List<Alert> getActiveAlerts() {
        return alertRepository.findByStatus(AlertStatus.ACTIVE);
    }

    @Transactional
    public Alert acknowledgeAlert(String alertId) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado"));

        Alert acknowledgedAlert = new Alert(
                alert.id(),
                alert.deviceId(),
                alert.type(),
                alert.severity(),
                alert.message(),
                alert.createdAt(),
                LocalDateTime.now(),
                "SYSTEM",
                AlertStatus.ACKNOWLEDGED);

        return alertRepository.save(acknowledgedAlert);
    }

    @Transactional
    public void processMetricAlert(String deviceId, Map<String, Double> metrics, ReadingStatus status) {
        if (status != ReadingStatus.NORMAL) {
            AlertEvent event = new AlertEvent(
                    deviceId,
                    AlertType.METRIC_THRESHOLD,
                    (status == ReadingStatus.CRITICAL) ? AlertSeverity.CRITICAL : AlertSeverity.WARNING,
                    Map.of(
                            "metrics", metrics,
                            "status", status.toString(),
                            "message", "Anomalia detectada nas métricas"),
                    LocalDateTime.now());
            createAlert(event);
        }
    }
}
