package ru.otus.booklibrarymongodb.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.otus.booklibrarymongodb.TestData.BULGAKOV;

@DisplayName("DAO для работы с авторами ")
class AuthorRepoTest extends AbstractRepositoryTest{

    @Autowired
    private AuthorRepo authorRepo;

    @Test
    @DisplayName("достает автора без учета регистра")
    void getByNameIgnoreCase() {
        assertNotNull(authorRepo.getByNameIgnoreCase(BULGAKOV.toLowerCase()));
    }
}