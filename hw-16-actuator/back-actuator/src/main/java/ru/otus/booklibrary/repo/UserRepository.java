package ru.otus.booklibrary.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.booklibrary.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    Boolean existsByName(String username);

    Boolean existsByEmail(String email);
}
