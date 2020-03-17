package ru.otus.booklibrary.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.booklibrary.domain.Comment;
import ru.otus.booklibrary.repo.CommentRepo;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"})
@RestController
@RequiredArgsConstructor
@Slf4j
class CommentController {

    private final CommentRepo commentRepo;

    @GetMapping(value = "/comments/book/{bookId}")
    public List<Comment> findByBookId(@PathVariable("bookId") Long bookId) {
        log.info("getAll");
        return commentRepo.findAllByBookId(bookId);
    }

    @PostMapping(value = "/comments")
    public Comment create(@RequestBody Comment newAuthor) {
        log.info("create: {}", newAuthor);
        return commentRepo.save(newAuthor);
    }

    @PutMapping(value = "/comments/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody Comment forUpdate) {
        log.info("update: {}", forUpdate);
        commentRepo.save(forUpdate);
    }

    @DeleteMapping(value = "/comments/{id}")
    public void delete(@PathVariable("id") Long id) {
        log.info("delete");
        commentRepo.deleteById(id);
    }
}
