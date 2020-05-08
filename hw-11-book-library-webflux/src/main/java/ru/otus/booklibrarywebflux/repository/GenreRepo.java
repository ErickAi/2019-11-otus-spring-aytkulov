package ru.otus.booklibrarywebflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.booklibrarywebflux.domain.Genre;

public interface GenreRepo extends ReactiveMongoRepository<Genre, String> {

    Mono<Genre> getByNameIgnoreCase(String name);
}
