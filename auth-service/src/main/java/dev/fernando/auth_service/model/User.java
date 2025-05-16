package dev.fernando.auth_service.model;

import dev.fernando.auth_service.constant.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users", schema = "auth_service")
public class User {

    @Id
    private String email;
    private String password;
    private boolean isActive;
    private UserRole role;
}
