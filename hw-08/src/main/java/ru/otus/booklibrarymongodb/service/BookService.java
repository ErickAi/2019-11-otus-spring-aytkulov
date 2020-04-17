package ru.otus.booklibrarymongodb.service;

import ru.otus.booklibrarymongodb.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    Book getById(String id);

    Book getByName(String name);

    List<Book> getAllContainsAuthorName(String authorName);

    List<Book> findByGenre(String genreName);

    Book save(Book book);

    void deleteById(String id);
}
