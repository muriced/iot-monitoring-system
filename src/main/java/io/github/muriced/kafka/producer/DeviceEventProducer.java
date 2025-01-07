package io.github.muriced.kafka.producer;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.StructuredTaskScope;

import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import io.github.muriced.domain.event.DeviceEvent;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeviceEventProducer {
    private final KafkaTemplate<String, DeviceEvent> kafkaTemplate;
    private static final String TOPIC = "device-events";

    public DeviceEventProducer(KafkaTemplate<String, DeviceEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendDeviceEvent(DeviceEvent event) {
        try {
            CompletableFuture<SendResult<String, DeviceEvent>> future = kafkaTemplate.send(TOPIC, event.deviceId(),
                    event);
            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Failed to send device event: {}", ex.getMessage());
                } else {
                    log.info("Device event sent successfully: {}", event.deviceId());
                }
            });
        } catch (Exception e) {
            log.error("Error sending device event: {}", e.getMessage(), e);
            throw new KafkaException("Error sending device event", e);
        }
    }

    public void sendDeviceEvents(List<DeviceEvent> events) {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            events.forEach(event -> scope.fork(() -> {
                sendDeviceEvent(event);
                return null;
            }));
            scope.join();
            scope.throwIfFailed();
        } catch (Exception e) {
            log.error("Error sending batch of device events: {}", e.getMessage(), e);
            throw new KafkaException("Error sending batch of device events", e);
        }
    }
}