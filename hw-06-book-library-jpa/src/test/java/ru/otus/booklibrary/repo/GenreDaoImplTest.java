package ru.otus.booklibrary.repo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.booklibrary.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.booklibrary.TestData.*;

@DataJpaTest
@Import(GenreDaoImpl.class)
@DisplayName(value = "DAO для работы с жанрами")
@Slf4j
class GenreDaoImplTest {

    @Autowired
    GenreDaoImpl genreDao;

    @Test
    @DisplayName(value = "достает из базы все жанры")
    void getAll() {
        List<Genre> allGenres = genreDao.getAll();
        assertEquals(allGenres.size(), genreDao.count());
    }

    @Test
    @DisplayName(value = "достает из базы жанр по названию")
    void getByName() {
        Genre genre = genreDao.getByName(GENRE.getName());
        assertEquals(genre.getId(), GENRE.getId());
    }

    @Test
    @DisplayName(value = "сохраняет новый жанр")
    void insert() {
        long expectedCount = genreDao.count() + 1;
        genreDao.insert(NEW_GENRE);
        assertEquals(expectedCount, genreDao.count());
    }
}