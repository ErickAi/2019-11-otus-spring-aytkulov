package ru.otus.booklibrary.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    public Comment create(@RequestBody Comment newComment) {
        log.info("create: {}", newComment);
        return commentRepo.save(newComment);
    }

    @PutMapping(value = "/comments")
    @PreAuthorize("#forUpdate.user.email == #authentication.name")
    public void update(@RequestBody Comment forUpdate, Authentication authentication) {
        log.info("update: {}", forUpdate);
        commentRepo.save(forUpdate);
    }

    @DeleteMapping(value = "/comments")
    @PreAuthorize("#forDelete.user.email == #authentication.name or hasRole('ADMIN')")
    public void delete(@RequestBody(required = false) Comment forDelete, Authentication authentication) {
        log.info("delete");
        commentRepo.delete(forDelete);
    }
}
