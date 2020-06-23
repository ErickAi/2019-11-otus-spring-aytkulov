package ru.otus.springbatch.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.springbatch.domain.nosql.BookMongo;

public interface BookRepoMongo extends MongoRepository<BookMongo, String> {
}
