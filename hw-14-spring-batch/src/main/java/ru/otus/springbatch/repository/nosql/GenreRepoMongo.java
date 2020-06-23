package ru.otus.springbatch.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.springbatch.domain.nosql.GenreMongo;

public interface GenreRepoMongo extends MongoRepository<GenreMongo, String> {
}
