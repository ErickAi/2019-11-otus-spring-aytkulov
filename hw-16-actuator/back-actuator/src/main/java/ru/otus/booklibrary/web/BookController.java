package ru.otus.booklibrary.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.otus.booklibrary.domain.Book;
import ru.otus.booklibrary.services.BookService;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"})
@RestController
@RequiredArgsConstructor
@Slf4j
@Secured("ROLE_USER")
class BookController {

    private final BookService bookService;

    @GetMapping(value = "/books")
    public List<Book> getAll() {
        log.info("GET: /books");
        return bookService.getAll();
    }

    @GetMapping(value = "/books/{id}")
    public Book getById(@PathVariable("id") Long id) {
        log.info("GET: /books/{}", id);
        return bookService.getById(id);
    }

    @GetMapping(value = "/books/name/{name}")
    public Book getByName(@PathVariable("name") String name) {
        log.info("GET: /books/name/{}", name);
        return bookService.getByName(name);
    }

    @GetMapping(value = "/books/author/{authorName}")
    public List<Book> getByAuthorName(@PathVariable("authorName") String authorName) {
        log.info("GET: /books/author/{}", authorName);
        return bookService.getAllContainsAuthorName(authorName);
    }

    @GetMapping(value = "/books/genre/{genreName}")
    public List<Book> getByGenre(@PathVariable("genreName") String genreName) {
        log.info("GET: /books/genre/{}", genreName);
        return bookService.findByGenre(genreName);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/books")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Book newBook) {
        log.info("POST: /books");
        bookService.save(newBook);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/books/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody Book forUpdate) {
        log.info("PUT: /books/{}", id);
        bookService.save(forUpdate);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/books/{id}")
    public void delete(@PathVariable("id") Long id) {
        log.info("DELETE: /books/{}", id);
        bookService.deleteById(id);
    }
}
