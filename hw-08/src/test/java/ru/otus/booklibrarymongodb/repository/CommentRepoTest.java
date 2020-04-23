package ru.otus.booklibrarymongodb.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.crossstore.ChangeSetPersister;
import ru.otus.booklibrarymongodb.domain.Book;
import ru.otus.booklibrarymongodb.domain.Comment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.otus.booklibrarymongodb.TestData.MASTER_AND_MARGARITA;

@DisplayName("DAO для работы с комментариями ")
@ComponentScan("ru.otus.booklibrarymongodb.events")
class CommentRepoTest extends AbstractRepositoryTest{

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Test
    @DisplayName("удаляет комментарии при удалении книги")
    void deleteCommentsWhenRelatedBookDeleted() throws ChangeSetPersister.NotFoundException {
        Book book = bookRepo.getByNameIgnoreCase(MASTER_AND_MARGARITA)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        bookRepo.deleteById(book.getId());
        List<Comment> comments = commentRepo.findAllByBookId(book.getId());
        assertTrue(comments.isEmpty());
    }
}