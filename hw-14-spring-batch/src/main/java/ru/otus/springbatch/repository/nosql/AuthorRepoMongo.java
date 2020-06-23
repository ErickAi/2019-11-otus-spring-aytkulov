package ru.otus.springbatch.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.springbatch.domain.nosql.AuthorMongo;

public interface AuthorRepoMongo extends MongoRepository<AuthorMongo, String> {
}
