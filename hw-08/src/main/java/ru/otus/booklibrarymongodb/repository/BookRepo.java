package ru.otus.booklibrarymongodb.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.booklibrarymongodb.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepo extends MongoRepository<Book, String> {

    Optional<Book> getByNameIgnoreCase(String name);

    List<Book> getAllByAuthorNameContainsIgnoreCase(String authorName);

    List<Book> findByGenres_NameIgnoreCase(String genreName);
}