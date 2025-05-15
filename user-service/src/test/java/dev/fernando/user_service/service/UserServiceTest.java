package dev.fernando.user_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.fernando.user_service.constant.UserRole;
import dev.fernando.user_service.dto.CreateUserDto;
import dev.fernando.user_service.dto.UpdateUserDto;
import dev.fernando.user_service.dto.ViewUserDto;
import dev.fernando.user_service.exception.UserAlreadyExistsException;
import dev.fernando.user_service.exception.UserNotFoundException;
import dev.fernando.user_service.mapper.UserMapper;
import dev.fernando.user_service.model.User;
import dev.fernando.user_service.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;
    private CreateUserDto createUserDto;
    private UpdateUserDto updateUserDto;

    @BeforeEach
    void setUp() {

        user = new User(
                UUID.randomUUID(),
                "testuser",
                "test@example.com",
                "password",
                System.currentTimeMillis(),
                UserRole.USER,
                true
        );

        createUserDto = new CreateUserDto(
                "testuser",
                "test@example.com",
                "password",
                "USER",
                true
        );

        updateUserDto = new UpdateUserDto(
                "updateduser",
                "updated@example.com",
                "newpassword",
                UserRole.ADMIN,
                false
        );
    }

    @Test
    void createUser_ShouldReturnViewUserDto_WhenUserIsCreated() {
        when(userRepository.findByEmail(createUserDto.email())).thenReturn(Optional.empty());
        when(userMapper.toEntity(createUserDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(passwordEncoder.encode(createUserDto.password())).thenReturn("encodedPassword");
        when(userMapper.toDto(user)).thenReturn(new ViewUserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.isActive()
        ));
        when(userMapper.toDto(user)).thenReturn(new ViewUserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.isActive()
        ));

        ViewUserDto result = userService.createUser(createUserDto);

        assertNotNull(result);
        assertNotEquals(user.getId(), result.id());
        assertEquals(user.getUsername(), result.username());
        assertEquals(user.getEmail(), result.email());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createUser_ShouldThrowException_WhenEmailAlreadyExists() {
        when(userRepository.findByEmail(createUserDto.email())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(createUserDto));
        verify(userRepository, never()).save(any());
    }

    @Test
    void getUserById_ShouldReturnViewUserDto_WhenUserExists() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(new ViewUserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.isActive()
        ));

        ViewUserDto result = userService.getUserById(user.getId());

        assertNotNull(result);
        assertEquals(user.getId(), result.id());
        assertEquals(user.getUsername(), result.username());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void updateUser_ShouldReturnViewUserDto_WhenUserIsUpdated() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toEntity(updateUserDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(new ViewUserDto(
                user.getId(),
                updateUserDto.username(),
                updateUserDto.email(),
                updateUserDto.role(),
                updateUserDto.isActive()
        ));

        ViewUserDto result = userService.updateUser(user.getId(), updateUserDto);

        assertNotNull(result);
        assertEquals(user.getId(), result.id());
        assertEquals(updateUserDto.username(), result.username());
        assertEquals(updateUserDto.email(), result.email());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, updateUserDto));
        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUser_ShouldCallRepository_WhenUserExists() {
        when(userRepository.existsById(user.getId())).thenReturn(true);

        userService.deleteUser(user.getId());

        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));
        verify(userRepository, never()).deleteById(any());
    }
}