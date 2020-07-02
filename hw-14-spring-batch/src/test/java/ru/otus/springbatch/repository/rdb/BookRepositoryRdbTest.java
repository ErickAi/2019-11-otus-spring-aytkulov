package ru.otus.springbatch.repository.rdb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.springbatch.TestData.BOOKS_COUNT;

@DataJpaTest
class BookRepositoryRdbTest {
    @Autowired
    private BookRepoRdb repo;

    @Test
    void findAll() {
        assertEquals(repo.count(), BOOKS_COUNT);
    }

}