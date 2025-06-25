package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.dto.SessionDeviceTypeCodeDTO;
import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query(value = """
        SELECT
           s.id,
           s.device_type as deviceTypeCode,
           s.ended_at_utc as endedAtUtc,
           s.started_at_utc as startedAtUtc,
           s.user_id as userId
        FROM sessions s
        WHERE device_type = :#{#deviceType.code}
        ORDER BY started_at_utc ASC
        LIMIT 1
        """, nativeQuery = true)
    Optional<SessionDeviceTypeCodeDTO> getFirstSessionByDeviceType(DeviceType deviceType);

    @Query(value = """
        SELECT
           s.id,
           s.device_type as deviceTypeCode,
           s.ended_at_utc as endedAtUtc,
           s.started_at_utc as startedAtUtc,
           s.user_id as userId
        FROM sessions s
        JOIN users u ON s.user_id = u.id
        WHERE u.deleted = false
        AND s.ended_at_utc < :endDate
        ORDER BY started_at_utc DESC
        """, nativeQuery = true)
    List<SessionDeviceTypeCodeDTO> getSessionsFromActiveUsersEndedBeforeDate(LocalDateTime endDate);
}