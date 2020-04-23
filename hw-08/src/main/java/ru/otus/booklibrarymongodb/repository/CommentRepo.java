package ru.otus.booklibrarymongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.booklibrarymongodb.domain.Comment;

import java.util.List;

public interface CommentRepo extends MongoRepository<Comment, String> {

    List<Comment> findAllByBookId(String bookId);

    void deleteAllByBookId(String bookId);
}
