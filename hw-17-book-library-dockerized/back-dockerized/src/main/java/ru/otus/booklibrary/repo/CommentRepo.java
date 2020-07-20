package ru.otus.booklibrary.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.booklibrary.domain.Comment;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBookId(Long bookId);
}
