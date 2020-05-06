package ru.otus.booklibrary.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.booklibrary.domain.Book;
import ru.otus.booklibrary.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.booklibrary.TestData.*;

@JdbcTest
@Import(BookDaoImpl.class)
@DisplayName(value = "DAO для работы с книгами")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class BookDaoImplTest {

    @Autowired
    private BookDaoImpl bookDao;

    @Test
    @Order(1)
    @DisplayName(value = "достает из базы все книги")
    void getAll() {
        List<Book> allBooks = bookDao.getAll();
        assertEquals(allBooks.size(), bookDao.count());
    }

    @Test
    @Order(2)
    @DisplayName(value = "достает из базы книгу по ID")
    void getById() {
        log.info(bookDao.getById(1).toString());
    }

    @Test
    @Order(3)
    @DisplayName(value = "достает из базы книгу по названию")
    void getByName() {
        log.info(bookDao.getByName(BOOK.getName()).toString());
    }

    @Test
    @Order(4)
    @DisplayName(value = "достает из базы список книг по автору")
    void getByAuthor() {
        List<Book> byAuthor = bookDao.getByAuthor(AUTHOR_SAPKOWSKI.getName());
        for (Book book : byAuthor) {
            log.info(book.toString());
        }
        assertEquals(SAPKOWSKI_BOOKS_COUNT, byAuthor.size());
    }

    @Test
    @Order(5)
    @DisplayName(value = "достает из базы список книг по жанру")
    void getByGenre() {
        List<Book> byGenre = bookDao.getByGenre(GENRE_DRAMA.getName());
        for (Book book : byGenre) {
            log.info(book.toString());
        }
        assertEquals(DRAMA_BOOKS_COUNT, byGenre.size());
    }

    @Test
    @Order(6)
    @DisplayName(value = "добавляет книгу с автором и жанрами в базу")
    void insert() {
        int expectedCount = bookDao.count() + 1;
        bookDao.insert(NEW_BOOK);
        assertEquals(expectedCount, bookDao.count());
    }

    @Test
    @Order(7)
    @DisplayName(value = "удаляет книгу и связи с жанрами по ее ID")
    void deleteById() {
        int expectedCount = bookDao.count() - 1;
        bookDao.deleteById(1);
        assertEquals(expectedCount, bookDao.count());
    }

    @Test
    @DisplayName(value = "оборачивает EmptyResultDataAccessException в NotFoundException")
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