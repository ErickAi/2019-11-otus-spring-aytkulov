package ru.otus.booklibrary.security;

import lombok.Getter;
import org.springframework.lang.NonNull;
import ru.otus.booklibrary.domain.Role;
import ru.otus.booklibrary.domain.User;

@Getter
public class AuthUser extends org.springframework.security.core.userdetails.User {
    @NonNull
    private User user;

    public AuthUser(User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public boolean hasRole(Role role) {
        return user.getRoles().contains(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + user.getEmail() + '\'' +
                ", name='" + user.getName() + '\'' +
                ", id=" + user.getId() +
                ", roles=" + user.getRoles() +
                '}';
    }
}
