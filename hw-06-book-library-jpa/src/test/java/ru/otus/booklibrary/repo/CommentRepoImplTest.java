package ru.otus.booklibrary.repo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.booklibrary.domain.Comment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.booklibrary.TestData.BOOK;

@DataJpaTest
@Import(CommentDaoImpl.class)
@DisplayName(value = "DAO для работы с комментариями")
@Slf4j
class CommentRepoImplTest {

    @Autowired
    private CommentDaoImpl commentDao;

    @Test
    @DisplayName(value = "достает из базы список комментариев по книге")
    void getByAuthor() {
        List<Comment> byBookId = commentDao.getAllByBookId(BOOK.getId());
        for (Comment book : byBookId) {
            log.info(book.toString());
        }
        assertEquals(2, byBookId.size());
    }
}