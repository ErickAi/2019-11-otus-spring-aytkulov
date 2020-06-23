package ru.otus.booklibrarywebflux;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.booklibrarywebflux.domain.Author;
import ru.otus.booklibrarywebflux.domain.Book;
import ru.otus.booklibrarywebflux.domain.Comment;
import ru.otus.booklibrarywebflux.domain.Genre;

@DataMongoTest
public abstract class AbstractTestInitDb {

    @Autowired
    MongoTemplate mongoTemplate;

    @BeforeEach
    void initDb() {
        for (Author author : TestData.getAuthors()) {
            mongoTemplate.save(author);
        }
        for (Genre genre : TestData.getGenres()) {
            mongoTemplate.save(genre);
        }
        for (Book book : TestData.getBooks()) {
            mongoTemplate.save(book);
        }
        for (Comment comment : TestData.getComments()) {
            mongoTemplate.save(comment);
        }
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(Author.class);
        mongoTemplate.dropCollection(Genre.class);
        mongoTemplate.dropCollection(Book.class);
        mongoTemplate.dropCollection(Comment.class);
    }

}
