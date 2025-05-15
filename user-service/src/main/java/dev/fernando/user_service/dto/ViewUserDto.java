package dev.fernando.user_service.dto;

import java.util.UUID;

import dev.fernando.user_service.constant.UserRole;

public record ViewUserDto (
    UUID id,
    String username,
    String email,
    UserRole role,
    boolean isActive
){}