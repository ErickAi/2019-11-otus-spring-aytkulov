package ru.otus.booklibrarymongodb.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.booklibrarymongodb.domain.Book;
import ru.otus.booklibrarymongodb.service.BookService;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"})
@RestController
@RequiredArgsConstructor
@Slf4j
class BookController {

    private final BookService bookService;

    @GetMapping(value = "/books")
    public List<Book> getAll() {
        log.info("GET: /books");
        return bookService.getAll();
    }

    @GetMapping(value = "/books/{id}")
    public Book getById(@PathVariable("id") String id) {
        log.info("GET: /books/{}", id);
        return bookService.getById(id);
    }

    @GetMapping(value = "/books/name/{name}")
    public Book getByName(@PathVariable("name") String name) {
        log.info("GET: /books/name/{}", name);
        return bookService.getByName(name);
    }

    @PostMapping(value = "/books")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Book newBook) {
        log.info("POST: /books");
        bookService.save(newBook);
    }

    @PutMapping(value = "/books/{id}")
    public void update(@PathVariable("id") String id, @RequestBody Book forUpdate) {
        log.info("PUT: /books/{}", id);
        bookService.save(forUpdate);
    }

    @DeleteMapping(value = "/books/{id}")
    public void delete(@PathVariable("id") String id) {
        log.info("DELETE: /books/{}", id);
        bookService.deleteById(id);
    }
}
