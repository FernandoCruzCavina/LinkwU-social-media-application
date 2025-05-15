package dev.fernando.auth_service.model;

import dev.fernando.auth_service.constant.UserRole;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "users", schema = "auth")
public class User {

    @Id
    private String email;
    private String password;
    private boolean isActive;
    private UserRole role;
}
