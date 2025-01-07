package io.github.muriced.simulation;

import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import io.github.muriced.domain.entities.Device;
import io.github.muriced.domain.enums.DeviceEventType;
import io.github.muriced.domain.event.DeviceEvent;
import io.github.muriced.service.DeviceService;
import io.github.muriced.kafka.producer.DeviceEventProducer;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceSimulator {

    private final DeviceService deviceService;
    private final DeviceEventProducer deviceEventProducer;
    private final Random random = new Random();

    @Scheduled(fixedRate = 5000) // Executa a cada 5 segundos
    public void simulateDeviceReadings() {
        List<Device> activeDevices = deviceService.getAllDevices(PageRequest.of(0, 10))
                .getContent();

        activeDevices.forEach(this::generateAndSendReading);
    }

    private void generateAndSendReading(Device device) {
        Map<String, Double> metrics = generateMetrics(device.type());
        Map<String, Object> eventData = new HashMap<>(metrics);

        DeviceEvent event = new DeviceEvent(
                device.id(),
                DeviceEventType.READING,
                eventData,
                LocalDateTime.now());

        deviceEventProducer.sendDeviceEvent(event);
        log.debug("Generated reading for device {}: {}", device.id(), metrics);
    }

    private Map<String, Double> generateMetrics(String deviceType) {
        Map<String, Double> metrics = new HashMap<>();

        switch (deviceType) {
            case "TEMPERATURE" -> {
                metrics.put("temperature", 20.0 + random.nextDouble() * 15); // 20-35°C
                metrics.put("humidity", 30.0 + random.nextDouble() * 40); // 30-70%
            }
            case "PRESSURE" -> {
                metrics.put("pressure", 980 + random.nextDouble() * 40); // 980-1020 hPa
            }
            case "LIGHT" -> {
                metrics.put("luminosity", random.nextDouble() * 1000); // 0-1000 lux
            }
            default -> log.warn("Unknown device type: {}", deviceType);
        }

        return metrics;
    }
}
