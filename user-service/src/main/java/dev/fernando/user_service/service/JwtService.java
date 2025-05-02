package dev.fernando.user_service.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import dev.fernando.user_service.model.User;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.refresh}")
    private long expirationTimeRefreshToken;

    public String generateToken(User user, long expirationTime) {
        String userJson = user.toString();

        return JWT.create()
            .withSubject(userJson)
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
            .withIssuer("api-user-linkwU")
            .sign(Algorithm.HMAC256(secret.getBytes()));
    }

    public String verifyToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret.getBytes()))
            .withIssuer("api-user-linkwU")
            .build()
            .verify(token)
            .getSubject();
    }

    public boolean isTokenExpired(String token) {
        return JWT.require(Algorithm.HMAC256(secret.getBytes()))
            .withIssuer("api-user-linkwU")
            .build()
            .verify(token)
            .getExpiresAt()
            .before(new Date());
    }

    public String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null){
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}
