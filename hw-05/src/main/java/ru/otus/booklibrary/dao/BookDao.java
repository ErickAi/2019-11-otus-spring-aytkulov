package ru.otus.booklibrary.dao;


import ru.otus.booklibrary.domain.Book;

import java.util.List;

public interface BookDao {

    Book getById(int id);

    List<Book> getAll();

    void insert(Book book);

    void deleteById(int id);
}