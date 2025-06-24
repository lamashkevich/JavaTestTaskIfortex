package com.example.java_ifortex_test_task.service;

import com.example.java_ifortex_test_task.dto.UserResponseDTO;
import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.mapper.UserMapper;
import com.example.java_ifortex_test_task.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDTO getUserWithMostSessions() {
        log.info("Getting user with most Sessions");
        return userRepository
                .getUserWithMostSessions()
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public List<UserResponseDTO> getUsersWithAtLeastOneMobileSession() {
        log.info("Getting users with at least one mobile session");
        return userRepository
                .getUsersWithAtLeastOneSessionByDeviceType(DeviceType.MOBILE)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }
}
