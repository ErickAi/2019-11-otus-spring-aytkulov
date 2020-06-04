package ru.otus.booklibrary.repo;

import ru.otus.booklibrary.domain.Author;

public interface AuthorDao {

    Long count();

    Author getByName(String name);

    Author insert(Author author);

}
