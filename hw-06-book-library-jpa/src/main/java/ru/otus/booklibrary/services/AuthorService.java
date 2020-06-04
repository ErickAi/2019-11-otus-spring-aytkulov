package ru.otus.booklibrary.services;

import ru.otus.booklibrary.domain.Author;

public interface AuthorService {

    Author getByName(String authorName);
}
