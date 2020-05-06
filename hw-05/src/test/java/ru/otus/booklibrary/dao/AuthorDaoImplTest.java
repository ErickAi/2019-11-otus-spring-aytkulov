package ru.otus.booklibrary.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.booklibrary.domain.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.booklibrary.TestData.AUTHOR;
import static ru.otus.booklibrary.TestData.NEW_AUTHOR;

@JdbcTest
@Import(AuthorDaoImpl.class)
@DisplayName(value = "DAO для работы с авторами")
@Slf4j
class AuthorDaoImplTest {

    @Autowired
    AuthorDaoImpl authorDao;

    @Test
    @DisplayName(value = "достает из базы автора по имени")
    void getByName() {
        Author author = authorDao.getByName(AUTHOR.getName());
        assertEquals(author.getId(), AUTHOR.getId());
    }

    @Test
    @DisplayName(value = "сохраняет нового автора")
    void insert() {
        int expectedCount = authorDao.count() + 1;
        authorDao.insert(NEW_AUTHOR);
        assertEquals(expectedCount, authorDao.count());
    }
}