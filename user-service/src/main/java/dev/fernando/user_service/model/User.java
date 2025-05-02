package dev.fernando.user_service.model;

import java.util.UUID;

import dev.fernando.user_service.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    private UUID id;
    private String username;
    private String email;
    private String password;
    private long createdAt;
    private UserRole role;
    private boolean isActive;
}
