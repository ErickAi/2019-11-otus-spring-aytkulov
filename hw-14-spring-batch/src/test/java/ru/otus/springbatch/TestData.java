package ru.otus.springbatch;

import ru.otus.springbatch.domain.nosql.AuthorMongo;
import ru.otus.springbatch.domain.nosql.GenreMongo;

import java.util.Set;

public class TestData {

    public static final int BOOKS_COUNT = 26;

    public static final AuthorMongo NEW_AUTHOR_MONGO = new AuthorMongo("new mongo author");
    public static final Set<GenreMongo> NEW_GENRE_MONGO_SET = Set.of(
            new GenreMongo("first new genre"),
            new GenreMongo("second new genre"));
}
