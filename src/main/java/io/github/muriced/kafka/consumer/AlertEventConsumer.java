package io.github.muriced.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import io.github.muriced.domain.entities.Alert;
import io.github.muriced.domain.event.AlertEvent;
import io.github.muriced.service.AlertService;
import io.github.muriced.controller.WebSocketController;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertEventConsumer {

    private final AlertService alertService;
    private final WebSocketController webSocketController;

    @KafkaListener(topics = "alert-events", groupId = "alert-processing-group", containerFactory = "alertEventKafkaListenerContainerFactory")
    public void consume(
            @Payload AlertEvent alert,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp) {
        try {
            log.info("Received alert: {} for device: {} from partition: {}",
                    alert.type(), alert.deviceId(), partition);

            Alert createdAlert = alertService.createAlert(alert);
            webSocketController.sendAlert(createdAlert);

            log.debug("Alert processed successfully: {}", createdAlert.id());
        } catch (Exception e) {
            log.error("Error processing alert: {}", e.getMessage(), e);
        }
    }
}
