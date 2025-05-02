package dev.fernando.user_service.dto;

public record AuthenticationToken(
    String token,
    String refreshToken
) {
}
