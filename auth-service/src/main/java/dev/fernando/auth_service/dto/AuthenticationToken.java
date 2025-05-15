package dev.fernando.auth_service.dto;

public record AuthenticationToken(
    String token,
    String refreshToken
) {
}
