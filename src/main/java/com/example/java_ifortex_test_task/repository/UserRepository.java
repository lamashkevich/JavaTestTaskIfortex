package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
        SELECT u.* FROM users u
        JOIN sessions s ON u.id = s.user_id
        WHERE u.deleted = false
        AND s.device_type = :#{#deviceType.code}
        GROUP BY u.id
        ORDER BY MAX(s.started_at_utc) DESC
        """, nativeQuery = true)
    List<User> getUsersWithAtLeastOneSessionByDeviceType(DeviceType deviceType);

    @Query(value = """
        SELECT u.* FROM users u
        JOIN sessions s ON u.id = s.user_id
        WHERE u.deleted = false
        GROUP BY u.id
        ORDER BY COUNT(s.id) DESC
        LIMIT 1
        """, nativeQuery = true)
    Optional<User> getUserWithMostSessions();
}
