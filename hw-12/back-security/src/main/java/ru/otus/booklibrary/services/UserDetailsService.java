package ru.otus.booklibrary.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.otus.booklibrary.domain.User;
import ru.otus.booklibrary.repo.UserRepository;

import static ru.otus.booklibrary.util.ValidationUtil.checkFoundOptional;

@Slf4j
@AllArgsConstructor
@Service("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) {
        log.debug("Authenticating {}", email);
        User user = checkFoundOptional(
                userRepository.findByEmailIgnoreCase(email), "User not found. email: " + email);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.getRoles());
    }
}
