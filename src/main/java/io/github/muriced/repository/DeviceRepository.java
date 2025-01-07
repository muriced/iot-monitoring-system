package io.github.muriced.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import io.github.muriced.domain.entities.Device;
import io.github.muriced.domain.enums.DeviceStatus;

@Repository
public interface DeviceRepository extends MongoRepository<Device, String> {

    Optional<Device> findByName(String name);

    List<Device> findByStatus(DeviceStatus status);

    List<Device> findByType(String type);

    @Query("{'location': ?0, 'status': ?1}")
    List<Device> findByLocationAndStatus(String location, DeviceStatus status);

    @Query("{'lastUpdateTime': {$lt: ?0}}")
    List<Device> findInactiveDevices(LocalDateTime threshold);
}
