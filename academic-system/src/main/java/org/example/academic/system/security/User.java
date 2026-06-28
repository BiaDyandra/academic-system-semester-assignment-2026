package org.example.academic.system.security;

import lombok.Getter;
import lombok.ToString;
import java.util.Objects;

@Getter
@ToString(exclude = "password")
public class User {

    private final String username;
    private final String password;
    private final Role role;

    public User(String username, String password, Role role) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("O nome de usuario nao pode ser vazio.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("A senha nao pode ser vazia.");
        }
        if (role == null) {
            throw new IllegalArgumentException("O papel do usuario nao pode ser nulo.");
        }

        this.username = username.trim();
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
