package ru.otus.booklibrary.services;

import ru.otus.booklibrary.domain.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> getAll();

    Genre getByName(String genreName);
}
