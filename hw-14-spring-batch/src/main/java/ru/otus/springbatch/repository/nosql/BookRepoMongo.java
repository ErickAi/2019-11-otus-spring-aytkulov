package ru.otus.springbatch.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.springbatch.domain.nosql.BookMongo;

import java.util.Optional;

public interface BookRepoMongo extends MongoRepository<BookMongo, String> {

    Optional<BookMongo> findByName(String name);
}
