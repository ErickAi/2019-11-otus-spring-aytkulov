package ru.otus.booklibrarymongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.booklibrarymongodb.domain.Author;

public interface AuthorRepo extends MongoRepository<Author, String> {

    Author getByNameIgnoreCase(String name);
}
