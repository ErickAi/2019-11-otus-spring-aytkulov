package ru.otus.booklibrary.repo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.booklibrary.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DisplayName(value = "Класс BookRepo")
class BookRepoTest {

    @Autowired
    private BookRepo bookRepo;

    @Test
    @DisplayName(value = "достает книгу из базы по названию без учета регистра символов")
    void getByNameIgnoreCase() {
        Optional<Book> book = bookRepo.getByNameIgnoreCase("МцЫрИ");
        assertTrue(book.isPresent());
    }

    @Test
    @DisplayName(value = "достает книги из базы по части имени автора")
    void getAllByAuthorNameContainsIgnoreCase() {
        List<Book> books = bookRepo.getAllByAuthorNameContainsIgnoreCase("нт");
        assertEquals(2, books.size());
    }

    @Test
    @DisplayName(value = "достает книги из базы по названию жанра без учета регистра символов")
    void findByGenres_NameIgnoreCase() {
        List<Book> books = bookRepo.findByGenres_NameIgnoreCase("СкАзКа");
        assertEquals(3, books.size());
    }
}
