package dev.fernando.user_service.dto;

public record UpdateUserDto(
    String username,
    String email,
    String password,
    String role,
    boolean isActive
) {}