package ru.otus.booklibrarymongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.booklibrarymongodb.domain.Genre;

public interface GenreRepo extends MongoRepository<Genre, String> {

    Genre getByNameIgnoreCase(String name);
}
