package ru.otus.booklibrary.repo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.booklibrary.domain.Book;
import ru.otus.booklibrary.exception.NotFoundException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.booklibrary.TestData.*;

@DataJpaTest
@Import(BookDaoImpl.class)
@DisplayName(value = "DAO для работы с книгами")
@Slf4j
class BookDaoImplTest {

    @Autowired
    private BookDaoImpl bookDao;

    @Test
    @DisplayName(value = "достает из базы все книги")
    void getAll() {
        List<Book> allBooks = bookDao.getAll();
        assertThat(allBooks, hasItems(notNullValue()));
        assertEquals(bookDao.count(), allBooks.size());
    }

    @Test
    @DisplayName(value = "достает из базы книгу по ID")
    void getById() {
        assertNotNull(bookDao.getById(1L));

    }

    @Test
    @DisplayName(value = "достает из базы книгу по названию")
    void getByName() {
        Book book = bookDao.getByName(BOOK.getName());
        assertEquals(book.getName(), BOOK.getName());
    }

    @Test
    @DisplayName(value = "достает из базы список книг по автору")
    void getByAuthor() {
        List<Book> byAuthor = bookDao.getByAuthor(AUTHOR_SAPKOWSKI.getName());
        for (Book book : byAuthor) {
            log.info(book.toString());
        }
        assertEquals(SAPKOWSKI_BOOKS_COUNT, byAuthor.size());
    }

    @Test
    @DisplayName(value = "достает из базы список книг по жанру")
    void getByGenre() {
        List<Book> byGenre = bookDao.getByGenre(GENRE_DRAMA.getName());
        for (Book book : byGenre) {
            log.info(book.toString());
        }
        assertEquals(DRAMA_BOOKS_COUNT, byGenre.size());
    }

    @Test
    @DisplayName(value = "добавляет книгу с автором и жанрами в базу")
    void insert() {
        long expectedCount = bookDao.count() + 1;
        bookDao.saveOrUpdate(NEW_BOOK);
        assertEquals(expectedCount, bookDao.count());
    }

    @Test
    @DisplayName(value = "удаляет книгу и связи с жанрами по ее ID")
    void deleteById() {
        long expectedCount = bookDao.count() - 1;
        bookDao.deleteById(1L);
        assertEquals(expectedCount, bookDao.count());
    }

    @Test
    @DisplayName(value = "оборачивает NoResultException в NotFoundException если книги с запрашиваемым названием нет в библиотеке")
    void getByNameException() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> bookDao.getByName(NO_NAME));
        assertTrue(thrown.getMessage().contains("not found"));
    }

    @Test
    @DisplayName(value = "бросает ошибку в методе getByAuthor(\"Автор\") если автора не существует")
    void getByAuthorException() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> bookDao.getByAuthor(NO_NAME));
        assertTrue(thrown.getMessage().contains("not found"));
    }

    @Test
    @DisplayName(value = "бросает ошибку в методе getByGenre(\"Жанр\") если жанра не существует")
    void getByGenreException() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> bookDao.getByGenre(NO_NAME));
        assertTrue(thrown.getMessage().contains("not found"));
    }

}