package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.DeviceType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql(scripts = "/init.sql")
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUsersWithAtLeastOneSessionByDeviceType() {
        var deviceType = DeviceType.MOBILE;

        var result = userRepository.getUsersWithAtLeastOneSessionByDeviceType(deviceType);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(2L, result.get(0).getId());
        assertEquals(1L, result.get(1).getId());
    }

    @Test
    public void getUserWithMostSessions() {
        var result = userRepository.getUserWithMostSessions();

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

}