package ru.otus.booklibrarymongodb.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.otus.booklibrarymongodb.TestData.DRAMA;

@DisplayName("DAO для работы с жанрами ")
class GenreRepoTest extends AbstractRepositoryTest{

    @Autowired
    private GenreRepo genreRepo;

    @Test
    @DisplayName("достает жанр без учета регистра")
    void getByNameIgnoreCase() {
        assertNotNull(genreRepo.getByNameIgnoreCase(DRAMA.toLowerCase()));
    }
}