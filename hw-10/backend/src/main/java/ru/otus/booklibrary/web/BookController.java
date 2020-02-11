package ru.otus.booklibrary.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.booklibrary.domain.Book;
import ru.otus.booklibrary.services.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
class BookController {

    private final BookService bookService;

    @GetMapping(value = "/books")
    public List<Book> getAll() {
        log.info("getAll");
        return bookService.getAll();
    }

    @GetMapping(value = "/books/{id}")
    public Book getById(@PathVariable("id") Long id) {
        log.info("getById");
        return bookService.getById(id);
    }

    @PostMapping(value = "/books")
    public void create(@RequestBody Book newBook) {
        log.info("create");
        bookService.save(newBook);
    }

    @PutMapping(value = "/books/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody Book forUpdate) {
        log.info("update");
        bookService.save(forUpdate);
    }

    @DeleteMapping(value = "/books/{id}")
    public void delete(@PathVariable("id") Long id) {
        log.info("delete");
        bookService.deleteById(id);
    }
}
