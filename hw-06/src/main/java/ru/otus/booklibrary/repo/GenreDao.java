package ru.otus.booklibrary.repo;

import ru.otus.booklibrary.domain.Genre;

import java.util.List;

public interface GenreDao {

    Integer count();

    Genre getByName(String name);

    List<Genre> getAll();

    Genre insert(Genre genre);
}
