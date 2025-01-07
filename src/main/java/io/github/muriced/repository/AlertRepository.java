package io.github.muriced.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

import io.github.muriced.domain.entities.Alert;
import io.github.muriced.domain.enums.AlertStatus;
import io.github.muriced.domain.enums.AlertType;
import io.github.muriced.domain.enums.AlertSeverity;

@Repository
public interface AlertRepository extends MongoRepository<Alert, String> {

    List<Alert> findByDeviceId(String deviceId);

    List<Alert> findByStatus(AlertStatus status);

    List<Alert> findBySeverity(AlertSeverity severity);

    List<Alert> findByType(AlertType type);

    @Query("{'status': 'ACTIVE', 'severity': {$in: ['HIGH', 'CRITICAL']}}")
    List<Alert> findActiveCriticalAlerts();

    @Query("{'deviceId': ?0, 'createdAt': {$gte: ?1, $lte: ?2}}")
    List<Alert> findAlertsByDeviceAndTimeRange(
            String deviceId,
            LocalDateTime startTime,
            LocalDateTime endTime);

    List<Alert> findByStatusAndCreatedAtBefore(
            AlertStatus status,
            LocalDateTime threshold);
}
