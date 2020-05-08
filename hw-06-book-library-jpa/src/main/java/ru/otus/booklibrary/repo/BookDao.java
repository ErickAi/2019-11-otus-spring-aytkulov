package ru.otus.booklibrary.repo;


import ru.otus.booklibrary.domain.Book;

import java.util.List;

public interface BookDao {

    Long count();

    Book getById(Long id);

    Book getByName(String name);

    List<Book> getByAuthor(String author);

    List<Book> getByGenre(String genre);

    List<Book> getAll();

    Book saveOrUpdate(Book book);

    boolean deleteById(long id);
}