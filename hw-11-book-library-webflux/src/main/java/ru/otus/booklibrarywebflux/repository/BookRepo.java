package ru.otus.booklibrarywebflux.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.booklibrarywebflux.domain.Book;

public interface BookRepo extends ReactiveMongoRepository<Book, String> {

    Mono<Book> getByNameIgnoreCase(String name);
}