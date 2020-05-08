package ru.otus.booklibrarywebflux.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.booklibrarywebflux.domain.Comment;
import ru.otus.booklibrarywebflux.repository.CommentRepo;

@CrossOrigin(origins = {"http://localhost:8080"})
@RestController
@RequiredArgsConstructor
@Slf4j
class CommentController {

    private final CommentRepo commentRepo;

    @GetMapping(value = "/comments/book/{bookId}")
    public Flux<Comment> findByBookId(@PathVariable("bookId") String bookId) {
        log.info("findByBookId: {}", bookId);
        return commentRepo.findAllByBookId(bookId);
    }

    @PostMapping(value = "/comments")
    public Mono<Comment> create(@RequestBody Comment newComment) {
        log.info("create: {}", newComment);
        return commentRepo.save(newComment);
    }

    @PutMapping(value = "/comments/{id}")
    public Mono update(@PathVariable("id") String id, @RequestBody Comment forUpdate) {
        log.info("update: {}", forUpdate);
        return commentRepo.save(forUpdate);
    }

    @DeleteMapping(value = "/comments/{id}")
    public Mono deleteById(@PathVariable("id") String id) {
        log.info("deleteById: {}", id);
        return commentRepo.deleteById(id);
    }
}
