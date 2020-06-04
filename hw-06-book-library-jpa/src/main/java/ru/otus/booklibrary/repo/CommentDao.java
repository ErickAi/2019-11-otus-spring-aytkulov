package ru.otus.booklibrary.repo;

import ru.otus.booklibrary.domain.Comment;

import java.util.List;

public interface CommentDao {

    List<Comment> getAllByBookId(Long bookId);
}
