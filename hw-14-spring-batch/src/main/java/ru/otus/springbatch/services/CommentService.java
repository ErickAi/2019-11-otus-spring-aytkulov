package ru.otus.springbatch.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.springbatch.domain.jpa.CommentRdb;
import ru.otus.springbatch.domain.nosql.BookMongo;
import ru.otus.springbatch.domain.nosql.CommentMongo;
import ru.otus.springbatch.repository.nosql.BookRepoMongo;
import ru.otus.springbatch.repository.nosql.CommentRepoMongo;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CommentService implements GenericService<CommentRdb, CommentMongo> {

    private final CommentRepoMongo repo;
    private final BookRepoMongo bookRepo;

    @Override
    public CommentMongo transform(CommentRdb entity) {
        BookMongo book = bookRepo.findByName(entity.getBook().getName())
                .orElseThrow(() -> new RuntimeException("Something went wrong"));
        return new CommentMongo(book.getId(), entity.getEntry());
    }

    @Override
    public CommentMongo populate(CommentRdb entity) {
        return repo.save(transform(entity));
    }

    @Override
    public Set<CommentMongo> populateAll(Set<CommentRdb> entity) {
        throw new UnsupportedOperationException();
    }
}
