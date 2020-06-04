package ru.otus.booklibrary.repo;

import ru.otus.booklibrary.domain.Genre;

import java.util.List;

public interface GenreDao {

    Long count();

    Genre getByName(String name);

    List<Genre> getAll();

    Genre insert(Genre genre);
}
