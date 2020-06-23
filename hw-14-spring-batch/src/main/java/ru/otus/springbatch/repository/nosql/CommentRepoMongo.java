package ru.otus.springbatch.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.springbatch.domain.nosql.CommentMongo;

public interface CommentRepoMongo extends MongoRepository<CommentMongo, String> {
}
