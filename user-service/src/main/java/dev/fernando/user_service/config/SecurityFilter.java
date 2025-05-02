package dev.fernando.user_service.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.fernando.user_service.model.User;
import dev.fernando.user_service.repository.UserRepository;
import dev.fernando.user_service.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{

    private JwtService jwtService;
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtService.recoverToken(request);

        if(token != null){
            String userJson = jwtService.verifyToken(token);
            User userFromToken = new ObjectMapper().readValue(userJson, User.class);
            User user = userRepository.findById(userFromToken.getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        filterChain.doFilter(request, response);
    }
}
