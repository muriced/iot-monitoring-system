package io.github.muriced.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

import io.github.muriced.domain.entities.DeviceMetrics;

@Repository
public interface DeviceMetricsRepository extends MongoRepository<DeviceMetrics, String> {
    List<DeviceMetrics> findByDeviceIdOrderByTimestampDesc(String deviceId);

    List<DeviceMetrics> findByDeviceIdAndTimestampBetween(String deviceId, LocalDateTime start, LocalDateTime end);
}
