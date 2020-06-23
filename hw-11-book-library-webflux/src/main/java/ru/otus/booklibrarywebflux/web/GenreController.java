package ru.otus.booklibrarywebflux.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.booklibrarywebflux.domain.Genre;
import ru.otus.booklibrarywebflux.repository.GenreRepo;

@CrossOrigin(origins = {"http://localhost:8080"})
@RestController
@RequiredArgsConstructor
@Slf4j
class GenreController {

    private final GenreRepo genreRepo;

    @GetMapping(value = "/genres")
    public Flux<Genre> getAll() {
        log.info("getAll");
        return genreRepo.findAll();
    }

    @GetMapping(value = "/genres/{id}")
    public Mono<Genre> getById(@PathVariable("id") String id) {
        log.info("getById");
        return genreRepo.findById(id);
    }

    @PostMapping(value = "/genres")
    public Mono<Genre> create(@RequestBody Genre newGenre) {
        log.info("create: {}", newGenre);
        return genreRepo.save(newGenre);
    }

    @PutMapping(value = "/genres/{id}")
    public Mono<Genre> update(@PathVariable("id") String id, @RequestBody Genre forUpdate) {
        log.info("update: {}", forUpdate);
        return genreRepo.save(forUpdate);
    }

    @DeleteMapping(value = "/genres/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        log.info("delete");
        return genreRepo.deleteById(id);
    }
}
