package dev.fernando.user_service.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.fernando.user_service.dto.AuthenticationToken;
import dev.fernando.user_service.dto.CreateUserDto;
import dev.fernando.user_service.dto.LoginUser;
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
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationToken login(LoginUser loginUser){
        User user = userRepository.findByEmail(loginUser.email())
                .orElseThrow(() -> new UserNotFoundException(loginUser.email()));

        if (!passwordEncoder.matches(loginUser.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        String token = jwtService.generateToken(user, 54000);
        String refreshToken = jwtService.generateToken(user, 216000);

        return new AuthenticationToken(token, refreshToken);
    }

    public String refreshToken(String token) {
        if (jwtService.isTokenExpired(token)) {
            throw new IllegalArgumentException("Refresh token is not valid");
        }

        String userJson = jwtService.verifyToken(token);
        User userFromToken = userMapper.toEntity(userJson);
        User user = userRepository.findById(userFromToken.getId())
                .orElseThrow(() -> new UserNotFoundException(userFromToken.getId()));

        return jwtService.generateToken(user, 54000);
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
