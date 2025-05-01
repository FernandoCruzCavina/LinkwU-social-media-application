package dev.fernando.user_service.dto;

import java.util.UUID;

public record ViewUserDto (
    UUID id,
    String username,
    String email,
    String role,
    boolean isActive
){}