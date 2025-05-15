package dev.fernando.user_service.dto;

public record CreateUserDto(
    String username,
    String email,
    String password,
    String role,
    boolean isActive
) {}