package ru.otus.booklibrary.services;

import ru.otus.booklibrary.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    Book getById(Long id);

    Book getByName(String name);

    List<Book> getAllContainsAuthorName(String authorName);

    List<Book> findByGenre(String genreName);

    Book save(Book book);

    void deleteById(Long id);
}
