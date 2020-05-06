package ru.otus.booklibrarymongodb.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.booklibrarymongodb.domain.Comment;
import ru.otus.booklibrarymongodb.repository.CommentRepo;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"})
@RestController
@RequiredArgsConstructor
@Slf4j
class CommentController {

    private final CommentRepo commentRepo;

    @GetMapping(value = "/comments/book/{bookId}")
    public List<Comment> findByBookId(@PathVariable("bookId") String bookId) {
        log.info("findByBookId: {}", bookId);
        return commentRepo.findAllByBookId(bookId);
    }

    @PostMapping(value = "/comments")
    public Comment create(@RequestBody Comment newComment) {
        log.info("create: {}", newComment);
        return commentRepo.save(newComment);
    }

    @PutMapping(value = "/comments/{id}")
    public void update(@PathVariable("id") String id, @RequestBody Comment forUpdate) {
        log.info("update: {}", forUpdate);
        commentRepo.save(forUpdate);
    }

    @DeleteMapping(value = "/comments/{id}")
    public void deleteById(@PathVariable("id") String id) {
        log.info("deleteById: {}", id);
        commentRepo.deleteById(id);
    }
}
