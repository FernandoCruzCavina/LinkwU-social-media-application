package dev.fernando.user_service.service;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {

    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;

    public JwtService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateToken(Authentication authentication, long expirationTime) {
        String scopes = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("user-service-linkwU")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(expirationTime))
                .subject(authentication.getName())
                .claim("roles", scopes)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();   
    }

    public String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return authHeader.replace("Bearer", "");
    }

    public String verifyToken(String token) {
        return jwtDecoder.decode(token).getSubject();
    }

}
