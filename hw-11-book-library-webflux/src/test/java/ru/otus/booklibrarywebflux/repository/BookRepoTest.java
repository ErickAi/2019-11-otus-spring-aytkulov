package ru.otus.booklibrarywebflux.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.booklibrarywebflux.AbstractTestInitDb;
import ru.otus.booklibrarywebflux.TestData;
import ru.otus.booklibrarywebflux.domain.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.otus.booklibrarywebflux.TestData.*;

@DisplayName("Репозиторий для работы с книгами")
class BookRepoTest extends AbstractTestInitDb {

    @Autowired
    BookRepo bookRepo;

    @Test
    @DisplayName("ищет книгу по названию игнорируя регистр")
    void getByNameIgnoreCase() {
        StepVerifier.create(
                bookRepo.getByNameIgnoreCase(IDIOT.getName().toLowerCase()))
                .expectNextMatches(book -> book.equals(IDIOT))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("достает все книги")
    void getAll() {
        StepVerifier.create(
                bookRepo.findAll())
                .expectNextCount(TestData.getBooks().size())
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("возвращает книгу по ее ID")
    void getById() {
        StepVerifier.create(
                bookRepo.findById(IDIOT.getId()))
                .expectNextMatches(book -> book.equals(IDIOT))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("создает книгу")
    void create() {
        Mono<Book> newBook = bookRepo.save(DEMONS);

        StepVerifier.create(newBook)
                .assertNext(book -> assertNotNull(book.getId()))
                .expectComplete()
                .verify();

        StepVerifier.create(
                bookRepo.findAll())
                .expectNextCount(4)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("обновляет книгу")
    void update() {
        Book forUpdate = new Book(IDIOT);
        forUpdate.setName(NEW_NAME);
        Mono<Book> newBook = bookRepo.save(forUpdate);

        StepVerifier.create(newBook)
                .assertNext(book -> assertEquals(book.getName(), NEW_NAME))
                .expectComplete()
                .verify();

        StepVerifier.create(
                bookRepo.getByNameIgnoreCase(NEW_NAME))
                .expectNextCount(1)
                .expectComplete()
                .verify();

        StepVerifier.create(
                bookRepo.getByNameIgnoreCase(IDIOT.getName()))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }
}