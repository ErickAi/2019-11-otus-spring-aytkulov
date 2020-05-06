package ru.otus.booklibrary.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.booklibrary.domain.Author;
import ru.otus.booklibrary.domain.Genre;
import ru.otus.booklibrary.exception.NotFoundException;
import ru.otus.booklibrary.repo.AuthorRepo;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"})
@RestController
@RequiredArgsConstructor
@Slf4j
class AuthorController {

    private final AuthorRepo authorRepo;

    @GetMapping(value = "/authors")
    public List<Author> getAll() {
        log.info("getAll");
        return authorRepo.findAll();
    }

    @GetMapping(value = "/authors/{id}")
    public Author getById(@PathVariable("id") Long id) {
        log.info("getById");
        return authorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Author with id '" + id + "' not found."));
    }

    @PostMapping(value = "/authors")
    public void create(@RequestBody Author newAuthor) {
        log.info("create: {}", newAuthor);
        authorRepo.save(newAuthor);
    }

    @PutMapping(value = "/authors/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody Author forUpdate) {
        log.info("update: {}", forUpdate);
        authorRepo.save(forUpdate);
    }

    @DeleteMapping(value = "/authors/{id}")
    public void delete(@PathVariable("id") Long id) {
        log.info("delete");
        authorRepo.deleteById(id);
    }
}
