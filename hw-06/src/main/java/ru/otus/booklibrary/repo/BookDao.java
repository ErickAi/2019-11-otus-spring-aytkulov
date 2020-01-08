package ru.otus.booklibrary.repo;


import ru.otus.booklibrary.domain.Book;

import java.util.List;

public interface BookDao {

    Integer count();

    Book getById(int id);

    Book getByName(String name);

    List<Book> getByAuthor(String author);

    List<Book> getByGenre(String genre);

    List<Book> getAll();

    Book insert(Book book);

    boolean deleteById(int id);
}