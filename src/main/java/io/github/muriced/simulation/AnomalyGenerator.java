package io.github.muriced.simulation;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import io.github.muriced.domain.entities.Device;
import io.github.muriced.domain.event.DeviceEvent;
import io.github.muriced.domain.enums.DeviceEventType;
import io.github.muriced.service.DeviceService;
import io.github.muriced.kafka.producer.DeviceEventProducer;

import java.util.Random;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("simulation")
public class AnomalyGenerator {

    private final DeviceService deviceService;
    private final DeviceEventProducer deviceEventProducer;
    private final Random random = new Random();

    @Scheduled(fixedRate = 60000) // A cada minuto
    public void generateAnomaly() {
        if (random.nextDouble() < 0.3) { // 30% de chance de gerar anomalia
            Device randomDevice = selectRandomDevice();
            if (randomDevice != null) {
                generateAnomalyEvent(randomDevice);
            }
        }
    }

    private void generateAnomalyEvent(Device device) {
        Map<String, Double> anomalousMetrics = generateAnomalousMetrics(device.type());
        Map<String, Object> eventData = new HashMap<>(anomalousMetrics);

        DeviceEvent event = new DeviceEvent(
                device.id(),
                DeviceEventType.ERROR,
                eventData,
                LocalDateTime.now());

        deviceEventProducer.sendDeviceEvent(event);
        log.info("Generated anomaly for device {}: {}", device.id(), anomalousMetrics);
    }

    private Map<String, Double> generateAnomalousMetrics(String deviceType) {
        return switch (deviceType) {
            case "TEMPERATURE" -> Map.of(
                    "temperature", random.nextBoolean() ? 40.0 + random.nextDouble() * 10 : // 40-50°C
                            -10.0 + random.nextDouble() * 10, // -10-0°C
                    "humidity", 10.0 + random.nextDouble() * 10 // 10-20%
                );
            case "PRESSURE" -> Map.of(
                    "pressure", random.nextBoolean() ? 1100 + random.nextDouble() * 50 : // 1100-1150 hPa
                            900 + random.nextDouble() * 50 // 900-950 hPa
                );
            default -> {
                log.warn("Unknown device type for anomaly: {}", deviceType);
                yield Map.of();
            }
        };
    }

    private Device selectRandomDevice() {
        List<Device> devices = deviceService.getAllDevices(PageRequest.of(0, 10)).getContent();
        if (devices.isEmpty()) {
            log.warn("No devices available for anomaly generation");
            return null;
        }
        return devices.get(random.nextInt(devices.size()));
    }
}