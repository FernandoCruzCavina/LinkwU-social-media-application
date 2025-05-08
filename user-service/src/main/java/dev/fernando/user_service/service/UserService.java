package dev.fernando.user_service.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.fernando.user_service.dto.CreateUserDto;
import dev.fernando.user_service.dto.UpdateUserDto;
import dev.fernando.user_service.dto.ViewUserDto;
import dev.fernando.user_service.exception.UserAlreadyExistsException;
import dev.fernando.user_service.exception.UserNotFoundException;
import dev.fernando.user_service.mapper.UserMapper;
import dev.fernando.user_service.model.User;
import dev.fernando.user_service.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public ViewUserDto createUser(CreateUserDto userRequest) {
        if (userRepository.findByEmail(userRequest.email()).isPresent()) {
            throw new UserAlreadyExistsException(userRequest.email());
        }

        User user = userMapper.toEntity(userRequest);
        user.setId(UUID.randomUUID());
        user.setCreatedAt(System.currentTimeMillis());
        user.setPassword(passwordEncoder.encode(userRequest.password()));

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public ViewUserDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDto(user);
    }

    public ViewUserDto updateUser(UUID id, UpdateUserDto userRequest) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        User updatedUser = userMapper.toEntity(userRequest);
        updatedUser.setId(existingUser.getId());
        updatedUser.setCreatedAt(existingUser.getCreatedAt());

        User savedUser = userRepository.save(updatedUser);
        return userMapper.toDto(savedUser);
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
