package com.example.java_ifortex_test_task.service;

import com.example.java_ifortex_test_task.dto.SessionResponseDTO;
import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.mapper.SessionMapper;
import com.example.java_ifortex_test_task.repository.SessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    public SessionResponseDTO getFirstDesktopSession() {
        log.info("Getting first desktop session");
        return sessionRepository
                .getFirstSessionByDeviceType(DeviceType.DESKTOP)
                .map(sessionMapper::toEntity)
                .map(sessionMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));
    }

    public List<SessionResponseDTO> getSessionsFromActiveUsersEndedBefore2025() {
        log.info("Getting sessions from active users ended before 2025");
        var endDate = LocalDate.of(2025,1,1).atStartOfDay();
        return sessionRepository
                .getSessionsFromActiveUsersEndedBeforeDate(endDate)
                .stream()
                .map(sessionMapper::toEntity)
                .map(sessionMapper::toDto)
                .toList();
    }
}