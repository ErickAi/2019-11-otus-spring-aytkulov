package ru.otus.booklibrary.dao;

import ru.otus.booklibrary.domain.Author;

public interface AuthorDao {

    Integer count();

    Author getByName(String name);

    Author insert(Author author);

}
