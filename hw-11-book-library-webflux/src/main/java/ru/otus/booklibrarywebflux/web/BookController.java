package ru.otus.booklibrarywebflux.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.booklibrarywebflux.domain.Book;
import ru.otus.booklibrarywebflux.repository.BookRepo;
import ru.otus.booklibrarywebflux.repository.CommentRepo;

@CrossOrigin(origins = {"http://localhost:8080"})
@RestController
@RequiredArgsConstructor
@Slf4j
class BookController {

    private final BookRepo bookRepo;
    private final CommentRepo commentRepo;

    @GetMapping(value = "/books")
    public Flux<Book> getAll() {
        log.info("GET: /books");
        return bookRepo.findAll();
    }

    @GetMapping(value = "/books/{id}")
    public Mono<Book> getById(@PathVariable("id") String id) {
        log.info("GET: /books/{}", id);
        return bookRepo.findById(id);
    }

    @GetMapping(value = "/books/name/{name}")
    public Mono<Book> getByName(@PathVariable("name") String name) {
        log.info("GET: /books/name/{}", name);
        return bookRepo.getByNameIgnoreCase(name);
    }

    @PostMapping(value = "/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> create(@RequestBody Book newBook) {
        log.info("POST: /books");
        return bookRepo.save(newBook);
    }

    @PutMapping(value = "/books/{id}")
    public Mono<Book> update(@PathVariable("id") String id, @RequestBody Book forUpdate) {
        log.info("PUT: /books/{}", id);
        return bookRepo.save(forUpdate);
    }

    @DeleteMapping(value = "/books/{id}")
    public Mono delete(@PathVariable("id") String id) {
        log.info("DELETE: /books/{}", id);
        return Mono.when(commentRepo.deleteAllByBookId(id))
                .then(bookRepo.deleteById(id));
    }
}
