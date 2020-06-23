package ru.otus.springbatch.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.springbatch.domain.jpa.GenreRdb;
import ru.otus.springbatch.domain.nosql.GenreMongo;
import ru.otus.springbatch.repository.nosql.GenreRepoMongo;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenreService implements GenericService<GenreRdb, GenreMongo> {

    private final GenreRepoMongo repo;

    @Override
    public GenreMongo transform(GenreRdb entity) {
        return new GenreMongo(entity.getName());
    }

    @Override
    public GenreMongo populate(GenreRdb entity) {
        return repo.save(transform(entity));
    }

    @Override
    public Set<GenreMongo> populateAll(Set<GenreRdb> entity) {
        Set<GenreMongo> genreMongoSet = new HashSet<>();
        for (GenreRdb genreRdb :entity) {
            genreMongoSet.add(populate(genreRdb));
        }
        return genreMongoSet;
    }
}
