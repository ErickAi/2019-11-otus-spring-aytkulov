package ru.otus.booklibrary.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.booklibrary.domain.Book;
import ru.otus.booklibrary.services.BookService;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:80", "http://localhost"})
@RestController
@RequiredArgsConstructor
@Slf4j
class GenreController {

    private final BookService bookService;

    @GetMapping(value = "/genres")
    public List<Book> getAll() {
        log.info("getAll");
        return bookService.getAll();
    }

    @GetMapping(value = "/genres/{id}")
    public Book getById(@PathVariable("id") Long id) {
        log.info("getById");
        return bookService.getById(id);
    }

    @GetMapping(value = "/genres/name/{name}")
    public Book getByName(@PathVariable("name") String name) {
        log.info("getByName");
        return bookService.getByName(name);
    }

    @PostMapping(value = "/genres")
    public void create(@RequestBody Book newBook) {
        log.info("create");
        bookService.save(newBook);
    }

    @PutMapping(value = "/genres/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody Book forUpdate) {
        log.info("update");
        bookService.save(forUpdate);
    }

    @DeleteMapping(value = "/genres/{id}")
    public void delete(@PathVariable("id") Long id) {
        log.info("delete");
        bookService.deleteById(id);
    }
}
