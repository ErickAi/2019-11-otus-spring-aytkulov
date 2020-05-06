package ru.otus.booklibrary.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.booklibrary.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, Long> {

    Optional<Book> getByNameIgnoreCase(String name);

    List<Book> getAllByAuthorNameContainsIgnoreCase(String authorName);

    List<Book> findByGenres_NameIgnoreCase(String genreName);
}