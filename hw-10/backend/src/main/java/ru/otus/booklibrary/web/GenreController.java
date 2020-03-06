package ru.otus.booklibrary.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.booklibrary.domain.Genre;
import ru.otus.booklibrary.exception.NotFoundException;
import ru.otus.booklibrary.repo.GenreRepo;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"})
@RestController
@RequiredArgsConstructor
@Slf4j
class GenreController {

    private final GenreRepo genreRepo;

    @GetMapping(value = "/genres")
    public List<Genre> getAll() {
        log.info("getAll");
        return genreRepo.findAll();
    }

    @GetMapping(value = "/genres/{id}")
    public Genre getById(@PathVariable("id") Long id) {
        log.info("getById");
        return genreRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre with id '" + id + "' not found."));
    }

    @PostMapping(value = "/genres")
    public void create(@RequestBody Genre newGenre) {
        log.info("create: {}", newGenre);
        genreRepo.save(newGenre);
    }

    @PutMapping(value = "/genres/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody Genre forUpdate) {
        log.info("update: {}", forUpdate);
        genreRepo.save(forUpdate);
    }

    @DeleteMapping(value = "/genres/{id}")
    public void delete(@PathVariable("id") Long id) {
        log.info("delete");
        genreRepo.deleteById(id);
    }
}
