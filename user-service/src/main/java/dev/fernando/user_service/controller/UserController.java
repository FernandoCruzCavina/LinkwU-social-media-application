package dev.fernando.user_service.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.fernando.user_service.dto.CreateUserDto;
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

    @PostMapping("/create")
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
