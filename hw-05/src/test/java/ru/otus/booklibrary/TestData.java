package ru.otus.booklibrary;

import ru.otus.booklibrary.domain.Author;
import ru.otus.booklibrary.domain.Book;
import ru.otus.booklibrary.domain.Genre;

import java.util.Arrays;
import java.util.HashSet;

public class TestData {
    public static final String NO_NAME = "No name";
    public static final int SAPKOWSKI_BOOKS_COUNT = 7;
    public static final Author AUTHOR = new Author(2, "author");
    public static final Author AUTHOR_SAPKOWSKI = new Author(11, "Анджей Сапковский");
    public static final Author NEW_AUTHOR = new Author(null, "New Author");


    public static final int DRAMA_BOOKS_COUNT = 4;
    public static final Genre GENRE = new Genre(2, "genre");
    public static final Genre GENRE_DRAMA = new Genre(6, "Драма");
    public static final Genre NEW_GENRE = new Genre(null, "New Genre");

    public static final Book BOOK = new Book(3, "book", AUTHOR,
            new HashSet<>(Arrays.asList(GENRE, GENRE_DRAMA)));
    public static final Book NEW_BOOK = new Book(null, "new book", AUTHOR,
            new HashSet<>(Arrays.asList(GENRE, GENRE_DRAMA)));

}
