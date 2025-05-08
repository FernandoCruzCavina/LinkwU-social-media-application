package dev.fernando.user_service.model;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dev.fernando.user_service.constant.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "user", schema = "user_service")
public class User{

    @Id
    private UUID id;
    private String username;
    private String email;
    private String password;
    private long createdAt;
    private UserRole role;
    private boolean isActive;

    public User(String username, String email, String password, long createdAt, UserRole role, boolean isActive) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.role = role;
        this.isActive = isActive;
    }
}
