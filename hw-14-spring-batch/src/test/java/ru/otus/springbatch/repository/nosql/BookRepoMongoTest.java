package ru.otus.springbatch.repository.nosql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.springbatch.domain.nosql.AuthorMongo;
import ru.otus.springbatch.domain.nosql.BookMongo;
import ru.otus.springbatch.domain.nosql.GenreMongo;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.otus.springbatch.TestData.NEW_AUTHOR_MONGO;
import static ru.otus.springbatch.TestData.NEW_GENRE_MONGO_SET;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.springbatch.config", "ru.otus.springbatch.repository.nosql"})
class BookRepoMongoTest {

    @Autowired
    private BookRepoMongo bookRepo;
    @Autowired
    private AuthorRepoMongo authorRepo;
    @Autowired
    private GenreRepoMongo genreRepo;

    @Test
    void saveWithRelations() {
        AuthorMongo savedAuthor = authorRepo.save(NEW_AUTHOR_MONGO);
        Set<GenreMongo> savedGenres = new HashSet<>();
        for (GenreMongo genreMongo : NEW_GENRE_MONGO_SET) {
            savedGenres.add(genreRepo.save(genreMongo));
        }
        String bookId = bookRepo.save(new BookMongo("new Book", savedAuthor, savedGenres)).getId();

        BookMongo savedBook = bookRepo.findById(bookId).orElse(null);
        assertNotNull(savedBook);
    }
}