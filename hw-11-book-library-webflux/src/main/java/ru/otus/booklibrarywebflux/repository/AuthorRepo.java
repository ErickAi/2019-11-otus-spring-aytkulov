package ru.otus.booklibrarywebflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.booklibrarywebflux.domain.Author;

public interface AuthorRepo extends ReactiveMongoRepository<Author, String> {

}
