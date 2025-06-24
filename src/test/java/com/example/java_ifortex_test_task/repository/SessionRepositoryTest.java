package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.DeviceType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql(scripts = "/init.sql")
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SessionRepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;

    @ParameterizedTest
    @CsvSource({
            "DESKTOP, DESKTOP",
            "MOBILE, MOBILE"
    })
    public void getFirstSessionByDeviceType(DeviceType deviceType, DeviceType exceptedType) {
        var result = sessionRepository.getFirstSessionByDeviceType(deviceType);

        assertTrue(result.isPresent());
        assertEquals(exceptedType, result.get().getDeviceType());
    }

    @ParameterizedTest
    @CsvSource({
            "2025-06-23T23:00:00, 3",
            "2023-06-15T16:00:00, 2",
            "2023-06-15T15:00:00, 1"
    })
    public void getSessionsFromActiveUsersEndedBeforeDate(LocalDateTime date, Integer expectedSize) {
        var result = sessionRepository.getSessionsFromActiveUsersEndedBeforeDate(date);

        assertNotNull(result);
        assertEquals(expectedSize, result.size());
    }

}