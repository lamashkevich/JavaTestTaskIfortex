package com.example.java_ifortex_test_task.mapper;

import com.example.java_ifortex_test_task.dto.SessionDeviceTypeCodeDTO;
import com.example.java_ifortex_test_task.dto.SessionResponseDTO;
import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.Session;
import com.example.java_ifortex_test_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionMapper {

    private final UserRepository userRepository;

    public SessionResponseDTO toDto(Session session) {
        SessionResponseDTO dto = new SessionResponseDTO();
        dto.setId(session.getId());
        dto.setDeviceType(session.getDeviceType());
        dto.setStartedAtUtc(session.getStartedAtUtc());
        dto.setEndedAtUtc(session.getEndedAtUtc());

        if (session.getUser() != null) {
            dto.setUserId(session.getUser().getId());
            dto.setUserFullName(getUserFullName(session));
        }

        return dto;
    }

    private String getUserFullName(Session session) {
        if (session.getUser() == null) {
            return null;
        }
        return String.format("%s %s",
                session.getUser().getFirstName(),
                session.getUser().getLastName());
    }

    public Session toEntity(SessionDeviceTypeCodeDTO dto) {
        Session session = new Session();
        session.setId(Long.valueOf(dto.getId()));
        session.setDeviceType(DeviceType.fromCode(dto.getDeviceTypeCode()));
        session.setStartedAtUtc(dto.getStartedAtUtc().toLocalDateTime());
        session.setEndedAtUtc(dto.getEndedAtUtc().toLocalDateTime());

        if (dto.getUserId() != null) {
            session.setUser(userRepository.getReferenceById(dto.getUserId().longValue()));
        }

        return session;
    }

}