package ru.otus.booklibrary.services;

import ru.otus.booklibrary.domain.Book;

import java.util.List;

public interface BookService {

    Book getById(int id);

    Book getByName(String name);

    List<Book> getByAuthor(String author);

    List<Book> getByGenre(String genre);

    List<Book> getAll();

    Book save(Book book);

    boolean deleteById(int id);
}
