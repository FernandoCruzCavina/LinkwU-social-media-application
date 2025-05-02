package dev.fernando.user_service.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.fernando.user_service.dto.AuthenticationToken;
import dev.fernando.user_service.dto.CreateUserDto;
import dev.fernando.user_service.dto.LoginUser;
import dev.fernando.user_service.dto.UpdateUserDto;
import dev.fernando.user_service.dto.ViewUserDto;
import dev.fernando.user_service.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationToken> login(@RequestBody LoginUser loginUser) {
        AuthenticationToken token = userService.login(loginUser);

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody String refreshToken) {
        String newToken = userService.refreshToken(refreshToken);

        return ResponseEntity.status(HttpStatus.OK).body(newToken);
    }

    @PostMapping
    public ResponseEntity<ViewUserDto> createUser(@RequestBody CreateUserDto userRequest) {
        ViewUserDto userDto = userService.createUser(userRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewUserDto> getUserById(@PathVariable UUID id) {
        ViewUserDto userDto = userService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ViewUserDto> updateUser(@PathVariable UUID id, @RequestBody UpdateUserDto userRequest) {
        ViewUserDto userDto = userService.updateUser(id, userRequest);
        
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
