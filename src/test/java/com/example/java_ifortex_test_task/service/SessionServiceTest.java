package com.example.java_ifortex_test_task.service;

import com.example.java_ifortex_test_task.dto.SessionResponseDTO;
import com.example.java_ifortex_test_task.dto.SessionDeviceTypeCodeDTO;
import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.Session;
import com.example.java_ifortex_test_task.mapper.SessionMapper;
import com.example.java_ifortex_test_task.repository.SessionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SessionMapper sessionMapper;

    @InjectMocks
    private SessionService sessionService;

    @Test
    public void getFirstDesktopSession_whenSessionIsFound() {
        var session = Session.builder().id(1L).build();
        var sessionDto = SessionDeviceTypeCodeDTO.builder().id(session.getId().intValue()).build();
        var exceptedDto = SessionResponseDTO.builder().id(session.getId()).build();

        when(sessionRepository.getFirstSessionByDeviceType(DeviceType.DESKTOP))
                .thenReturn(Optional.of(sessionDto));
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(exceptedDto);

        var result = sessionService.getFirstDesktopSession();

        assertNotNull(result);
        assertEquals(exceptedDto, result);
        verify(sessionRepository).getFirstSessionByDeviceType(DeviceType.DESKTOP);
        verify(sessionMapper).toDto(session);
    }

    @Test
    public void getFirstDesktopSession_whenSessionNotFound() {
        when(sessionRepository.getFirstSessionByDeviceType(DeviceType.DESKTOP))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> sessionService.getFirstDesktopSession());
    }

    @Test
    public void getSessionsFromActiveUsersEndedBefore2025() {
        var date = LocalDate.of(2025, 1, 1).atStartOfDay();

        var sessionDto1 = SessionDeviceTypeCodeDTO.builder().id(1).build();
        var sessionDto2 = SessionDeviceTypeCodeDTO.builder().id(2).build();
        var session1 = Session.builder().id(1L).build();
        var session2 = Session.builder().id(2L).build();
        var exceptedDto1 = SessionResponseDTO.builder().id(session1.getId()).build();
        var exceptedDto2 = SessionResponseDTO.builder().id(session2.getId()).build();

        when(sessionRepository.getSessionsFromActiveUsersEndedBeforeDate(date))
                .thenReturn(List.of(sessionDto1, sessionDto2));

        when(sessionMapper.toEntity(sessionDto1)).thenReturn(session1);
        when(sessionMapper.toEntity(sessionDto2)).thenReturn(session2);

        when(sessionMapper.toDto(session1)).thenReturn(exceptedDto1);
        when(sessionMapper.toDto(session2)).thenReturn(exceptedDto2);

        var result = sessionService.getSessionsFromActiveUsersEndedBefore2025();

        assertNotNull(result);
        assertEquals(List.of(exceptedDto1, exceptedDto2), result);
        verify(sessionRepository).getSessionsFromActiveUsersEndedBeforeDate(date);
        verify(sessionMapper).toDto(session1);
        verify(sessionMapper).toDto(session2);
    }
}