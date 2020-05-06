package ru.otus.booklibrary.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.booklibrary.domain.Author;

public interface AuthorRepo extends JpaRepository<Author, Long> {

    Author getByNameIgnoreCase(String name);
}
