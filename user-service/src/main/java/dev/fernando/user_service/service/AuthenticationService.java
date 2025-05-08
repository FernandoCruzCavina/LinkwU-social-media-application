package dev.fernando.user_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.fernando.user_service.dto.AuthenticationToken;
import dev.fernando.user_service.dto.LoginUserDto;
import dev.fernando.user_service.exception.UserNotFoundException;
import dev.fernando.user_service.repository.UserRepository;

@Service
public class AuthenticationService {

    @Value("${jwt.expiration}")
    private long expirationTime;
    @Value("${jwt.refresh.expiration}")
    private long refreshExpirationTime;    

    JwtService jwtService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;

    AuthenticationService(JwtService jwtService, UserRepository userRepository, 
            PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationToken login(LoginUserDto loginUserDto) {
        userRepository.findByEmail(loginUserDto.email())
                .orElseThrow(() -> new UserNotFoundException(loginUserDto.email()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        authenticationManager.authenticate(authentication);

        String token = jwtService.generateToken(authentication, 54000);
        String refreshToken = jwtService.generateToken(authentication, 216000);

        return new AuthenticationToken(token, refreshToken);
    }

    public String refresh(Authentication authentication) {
        String newToken = jwtService.generateToken(authentication, expirationTime);

        return newToken;
        
    }
}
