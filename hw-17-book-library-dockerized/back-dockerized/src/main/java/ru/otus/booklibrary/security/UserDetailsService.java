package ru.otus.booklibrary.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.booklibrary.domain.User;
import ru.otus.booklibrary.repo.UserRepository;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service("userDetailsService")
@Transactional(readOnly = true)
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private UserRepository userRepository;

    @Override
    public AuthUser loadUserByUsername(final String email) {
        log.debug("Authenticating {}", email);
        Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(email);
        return new AuthUser(optionalUser.orElseThrow(
                () -> new UsernameNotFoundException("User '" + email + "' was not found.")));
    }
}
