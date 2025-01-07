package io.github.muriced.kafka.producer;

import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import io.github.muriced.domain.event.AlertEvent;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AlertEventProducer {
    private final KafkaTemplate<String, AlertEvent> kafkaTemplate;
    private static final String TOPIC = "alert-events";

    public AlertEventProducer(KafkaTemplate<String, AlertEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAlertEvent(AlertEvent alert) {
        try {
            ProducerRecord<String, AlertEvent> record = new ProducerRecord<>(
                    TOPIC,
                    alert.deviceId(),
                    alert);

            record.headers().add(new RecordHeader("severity", alert.severity().toString().getBytes()));

            CompletableFuture<SendResult<String, AlertEvent>> future = kafkaTemplate.send(record);
            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Falha ao enviar alerta: {}", ex.getMessage());
                } else {
                    log.info("Alerta enviado com sucesso: {}", alert.deviceId());
                }
            });
        } catch (Exception e) {
            log.error("Error sending alert: {}", e.getMessage(), e);
            throw new KafkaException("Erro de envio do alert event", e);
        }
    }
}