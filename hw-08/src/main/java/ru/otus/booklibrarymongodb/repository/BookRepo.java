package ru.otus.booklibrarymongodb.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.booklibrarymongodb.domain.Book;

import java.util.Optional;

public interface BookRepo extends MongoRepository<Book, String> {

    Optional<Book> getByNameIgnoreCase(String name);
}