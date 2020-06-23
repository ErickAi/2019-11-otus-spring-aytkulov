package ru.otus.springbatch.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.springbatch.domain.jpa.AuthorRdb;
import ru.otus.springbatch.domain.nosql.AuthorMongo;
import ru.otus.springbatch.repository.nosql.AuthorRepoMongo;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorService implements GenericService<AuthorRdb, AuthorMongo> {

    private final AuthorRepoMongo repo;

    @Override
    public AuthorMongo transform(AuthorRdb entity) {
        return new AuthorMongo(entity.getName());
    }

    @Override
    public AuthorMongo populate(AuthorRdb entity) {
        return repo.save(transform(entity));
    }

    @Override
    public Set<AuthorMongo> populateAll(Set<AuthorRdb> entity) {
        Set<AuthorMongo> authorMongoSet = new HashSet<>();
        for (AuthorRdb authorRdb :entity) {
            authorMongoSet.add(populate(authorRdb));
        }
        return authorMongoSet;
    }
}
