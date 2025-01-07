package io.github.muriced.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.muriced.config.ThresholdConfig;
import io.github.muriced.domain.dto.response.DeviceAnalytics;
import io.github.muriced.domain.entities.SensorReading;
import io.github.muriced.domain.enums.ReadingStatus;
import io.github.muriced.repository.SensorReadingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorDataService {

    private final SensorReadingRepository sensorReadingRepository;
    private final AlertService alertService;
    private final ThresholdConfig thresholdConfig;

    @Transactional
    public SensorReading processSensorReading(String deviceId, Map<String, Double> metrics) {
        SensorReading reading = new SensorReading(
                null,
                deviceId,
                metrics,
                Map.of(),
                LocalDateTime.now(),
                evaluateReadingStatus(metrics));

        SensorReading savedReading = sensorReadingRepository.save(reading);
        checkThresholds(savedReading);
        return savedReading;
    }

    public List<SensorReading> getDeviceReadings(
            String deviceId,
            LocalDateTime startDate,
            LocalDateTime endDate) {
        return sensorReadingRepository.findByDeviceIdAndTimeRange(
                deviceId,
                startDate,
                endDate);
    }

    public DeviceAnalytics getAnalytics(String filter1, String filter2) {
        List<SensorReading> readings = sensorReadingRepository.findAll(); // You can apply your filters here

        if (readings.isEmpty()) {
            return new DeviceAnalytics(
                    null,
                    Map.of(),
                    Map.of(),
                    Map.of(),
                    0,
                    0.0,
                    null,
                    null);
        }

        Map<String, List<Double>> metricsByType = readings.stream()
                .flatMap(reading -> reading.metrics().entrySet().stream())
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        Map<String, Double> averageReadings = new HashMap<>();
        Map<String, Double> maxReadings = new HashMap<>();
        Map<String, Double> minReadings = new HashMap<>();

        metricsByType.forEach((metricType, values) -> {
            DoubleSummaryStatistics stats = values.stream()
                    .mapToDouble(Double::doubleValue)
                    .summaryStatistics();

            averageReadings.put(metricType, stats.getAverage());
            maxReadings.put(metricType, stats.getMax());
            minReadings.put(metricType, stats.getMin());
        });

        SensorReading latestReading = readings.stream()
                .max(Comparator.comparing(SensorReading::timestamp))
                .orElseThrow();

        return new DeviceAnalytics(
                latestReading.deviceId(),
                averageReadings,
                maxReadings,
                minReadings,
                readings.size(),
                averageReadings.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0),
                latestReading.metadata().getOrDefault("location", null),
                latestReading.timestamp());
    }

    private ReadingStatus evaluateReadingStatus(Map<String, Double> metrics) {
        Map<String, Double> critical = thresholdConfig.criticalThresholds();
        Map<String, Double> warning = thresholdConfig.warningThresholds();

        for (Map.Entry<String, Double> entry : metrics.entrySet()) {
            String metric = entry.getKey();
            double value = entry.getValue();

            if (value > critical.getOrDefault(metric, Double.MAX_VALUE)) {
                return ReadingStatus.CRITICAL;
            }
            if (value > warning.getOrDefault(metric, Double.MAX_VALUE)) {
                return ReadingStatus.WARNING;
            }
        }
        return ReadingStatus.NORMAL;
    }

    private void checkThresholds(SensorReading reading) {
        if (reading.status() != ReadingStatus.NORMAL) {
            alertService.processMetricAlert(
                    reading.deviceId(),
                    reading.metrics(),
                    reading.status());
            log.warn("Threshold exceeded for device {}: {}",
                    reading.deviceId(),
                    reading.metrics());
        }
    }
}