package dev.fernando.auth_service.dto;

public record LoginUserDto(
    String email,
    String password
) {}
