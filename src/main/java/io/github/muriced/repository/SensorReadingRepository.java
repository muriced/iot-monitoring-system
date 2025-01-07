package io.github.muriced.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

import io.github.muriced.domain.entities.SensorReading;
import io.github.muriced.domain.enums.ReadingStatus;

@Repository
public interface SensorReadingRepository extends MongoRepository<SensorReading, String> {

        List<SensorReading> findByDeviceId(String deviceId);

        @Query("{'deviceId': ?0, 'timestamp': {$gte: ?1, $lte: ?2}}")
        List<SensorReading> findByDeviceIdAndTimeRange(
                        String deviceId,
                        LocalDateTime startTime,
                        LocalDateTime endTime);

        Page<SensorReading> findByDeviceIdOrderByTimestampDesc(
                        String deviceId,
                        Pageable pageable);

        List<SensorReading> findByStatus(ReadingStatus status);

        @Query("{'metrics.temperature': {$gt: ?0}}")
        List<SensorReading> findByTemperatureAbove(double temperature);
}
