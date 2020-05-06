package ru.otus.booklibrarymongodb.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.otus.booklibrarymongodb.TestData.MASTER_AND_MARGARITA;

@DisplayName("DAO для работы с книгами ")
class BookRepoTest extends AbstractRepositoryTest {

    @Autowired
    private BookRepo bookRepo;

    @Test
    @DisplayName("достает книгу без учета регистра")
    void getByNameIgnoreCase() {
        val book = bookRepo.getByNameIgnoreCase(MASTER_AND_MARGARITA.toLowerCase()).orElse(null);
        assertNotNull(book);
    }
}