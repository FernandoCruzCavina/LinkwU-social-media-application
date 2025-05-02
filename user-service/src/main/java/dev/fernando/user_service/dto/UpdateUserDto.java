package dev.fernando.user_service.dto;

import dev.fernando.user_service.constant.UserRole;

public record UpdateUserDto(
    String username,
    String email,
    String password,
    UserRole role,
    boolean isActive
) {}