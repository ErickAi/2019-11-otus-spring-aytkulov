package ru.otus.booklibrary.repo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.booklibrary.domain.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.booklibrary.TestData.AUTHOR;
import static ru.otus.booklibrary.TestData.NEW_AUTHOR;

@DataJpaTest
@Import(AuthorDaoImpl.class)
@DisplayName(value = "DAO для работы с авторами")
@Slf4j
class AuthorDaoImplTest {

    @Autowired
    private AuthorDaoImpl authorDao;

    @Test
    @DisplayName(value = "достает из базы автора по имени")
    void getByName() {
        Author author = authorDao.getByName(AUTHOR.getName());
        assertEquals(AUTHOR, author);
    }

    @Test
    @DisplayName(value = "сохраняет нового автора")
    void insert() {
        authorDao.insert(NEW_AUTHOR);
        assertEquals(authorDao.getByName(NEW_AUTHOR.getName()), NEW_AUTHOR);
    }
}