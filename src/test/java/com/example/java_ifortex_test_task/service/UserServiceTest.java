package com.example.java_ifortex_test_task.service;

import com.example.java_ifortex_test_task.dto.UserResponseDTO;
import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.User;
import com.example.java_ifortex_test_task.mapper.UserMapper;
import com.example.java_ifortex_test_task.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void getUserWithMostSessions_whenUserIsFound() {
        var user = User.builder().email("test@test.test").build();
        var exceptedDto = UserResponseDTO.builder().email(user.getEmail()).build();

        when(userRepository.getUserWithMostSessions()).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(exceptedDto);

        var result = userService.getUserWithMostSessions();

        assertNotNull(result);
        assertEquals(exceptedDto, result);
        verify(userRepository).getUserWithMostSessions();
        verify(userMapper).toDto(user);
    }

    @Test
    public void getUserWithMostSessions_whenUserNotFound() {
        when(userRepository.getUserWithMostSessions())
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.getUserWithMostSessions());
    }

    @Test
    public void getUsersWithAtLeastOneMobileSession() {
        var deviceType = DeviceType.MOBILE;

        var user1 = User.builder().email("test1@test.test").build();
        var user2 = User.builder().email("test2@test.test").build();
        var exceptedDto1 = UserResponseDTO.builder().email(user1.getEmail()).build();
        var exceptedDto2 = UserResponseDTO.builder().email(user2.getEmail()).build();

        when(userRepository.getUsersWithAtLeastOneSessionByDeviceType(deviceType))
                .thenReturn(List.of(user1, user2));
        when(userMapper.toDto(user1)).thenReturn(exceptedDto1);
        when(userMapper.toDto(user2)).thenReturn(exceptedDto2);

        var result = userService.getUsersWithAtLeastOneMobileSession();

        assertNotNull(result);
        assertEquals(List.of(exceptedDto1, exceptedDto2), result);
        verify(userRepository).getUsersWithAtLeastOneSessionByDeviceType(deviceType);
        verify(userMapper).toDto(user1);
        verify(userMapper).toDto(user2);
    }
}