package ru.otus.booklibrarywebflux.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;
import ru.otus.booklibrarywebflux.AbstractTestInitDb;

import static ru.otus.booklibrarywebflux.TestData.IDIOT;
import static ru.otus.booklibrarywebflux.TestData.IDIOT_COMMENTS_COUNT;

@DisplayName("Репозиторий для работы с комментариями ")
class CommentRepoTest extends AbstractTestInitDb {

    @Autowired
    private CommentRepo commentRepo;

    @Test
    @DisplayName("достает из базы комментарии к книге по ее ID")
    void findAllByBookId() {
        StepVerifier.create(
                commentRepo.findAllByBookId(IDIOT.getId()))
                .expectNextCount(IDIOT_COMMENTS_COUNT)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("удаляет комментарии к конкретной книге")
    void deleteAllByBookId() {
        StepVerifier.create(
                commentRepo.deleteAllByBookId(IDIOT.getId()))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }
}