package ru.otus.booklibrary.repo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.booklibrary.domain.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@DisplayName(value = "Репозиторий для работы с книгами помимо дефолтных методов")
class BookRepoTest {

    @Autowired
    BookRepo bookRepo;

    @Test
    @DisplayName(value = "достает книгу по названию без учета регистра")
    void getByNameIgnoreCase() {
        String name = "идиот";
        assertNotNull(bookRepo.getByNameIgnoreCase(name));
    }

    @Test
    @DisplayName(value = "достает все книги по части имени автора без учета регистра")
    void getAllByAuthorNameContainsIgnoreCase() {
        List<Book> theWitcher = bookRepo.getAllByAuthorNameContainsIgnoreCase("Сапков");
        assertEquals(7, theWitcher.size());
    }

    @Test
    @DisplayName(value = "достает все книги по жанру без учета регистра")
    void findByGenres_NameIgnoreCase() {
        List<Book> fairyTales = bookRepo.findByGenres_NameIgnoreCase("сказка");
        assertEquals(3, fairyTales.size());
    }
}