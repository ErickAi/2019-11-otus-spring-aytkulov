package ru.otus.booklibrarywebflux.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.booklibrarywebflux.domain.Author;
import ru.otus.booklibrarywebflux.repository.AuthorRepo;

@CrossOrigin(origins = {"http://localhost:8080"})
@RestController
@RequiredArgsConstructor
@Slf4j
class AuthorController {

    private final AuthorRepo authorRepo;

    @GetMapping(value = "/authors")
    public Flux<Author> getAll() {
        log.info("getAll");
        return authorRepo.findAll();
    }

    @GetMapping(value = "/authors/{id}")
    public Mono<Author> getById(@PathVariable("id") String id) {
        log.info("getById");
        return authorRepo.findById(id);
    }

    @PostMapping(value = "/authors")
    public Mono<Author> create(@RequestBody Author newAuthor) {
        log.info("create: {}", newAuthor);
        return authorRepo.insert(newAuthor);
    }

    @PutMapping(value = "/authors/{id}")
    public Mono<Author> update(@PathVariable("id") String id, @RequestBody Author forUpdate) {
        log.info("update: {}", forUpdate);
        return authorRepo.save(forUpdate);
    }
}
