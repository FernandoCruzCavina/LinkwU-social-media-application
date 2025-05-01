package dev.fernando.user_service.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    private UUID id;
    private String username;
    private String email;
    private String password;
    private long createdAt;
    private String role;
    private boolean isActive;
}
