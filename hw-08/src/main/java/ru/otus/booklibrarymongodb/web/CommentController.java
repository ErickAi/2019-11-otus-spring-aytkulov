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
        log.info("getAll");
        return commentRepo.findAllByBookId(bookId);
    }

    @PostMapping(value = "/comments")
    public Comment create(@RequestBody Comment newAuthor) {
        log.info("create: {}", newAuthor);
        return commentRepo.save(newAuthor);
    }

    @PutMapping(value = "/comments/{id}")
    public void update(@PathVariable("id") String id, @RequestBody Comment forUpdate) {
        log.info("update: {}", forUpdate);
        commentRepo.save(forUpdate);
    }

    @DeleteMapping(value = "/comments/{id}")
    public void delete(@PathVariable("id") String id) {
        log.info("delete");
        commentRepo.deleteById(id);
    }
}
