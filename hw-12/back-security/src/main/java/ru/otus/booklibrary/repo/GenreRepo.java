package ru.otus.booklibrary.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.booklibrary.domain.Genre;

public interface GenreRepo extends JpaRepository<Genre, Long> {

    Genre getByNameIgnoreCase(String name);
}
