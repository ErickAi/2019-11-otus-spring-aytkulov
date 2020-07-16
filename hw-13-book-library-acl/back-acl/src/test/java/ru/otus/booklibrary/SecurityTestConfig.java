package ru.otus.booklibrary;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.otus.booklibrary.domain.Role;
import ru.otus.booklibrary.domain.User;
import ru.otus.booklibrary.security.AuthUser;

import java.util.Arrays;
import java.util.Set;

@TestConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {

        User admin = new User(1L, "Admin Admin", "a@a.a", "password",
                Set.of(Role.ROLE_ADMIN, Role.ROLE_USER));
        User user = new User(2L, "User User", "u@u.u", "password",
                Set.of(Role.ROLE_USER));

        return new InMemoryUserDetailsManager(Arrays.asList(new AuthUser(admin), new AuthUser(user)));
    }
}
