package ru.otus.booklibrary;

import ru.otus.booklibrary.domain.Author;
import ru.otus.booklibrary.domain.Book;
import ru.otus.booklibrary.domain.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class TestData {
    public static final Author AUTHOR_1 = new Author(1L, "cyan only book author");
    public static final Author AUTHOR_2 = new Author(2L, "magenta & yellow both book author");

    public static final Genre GENRE_RED = new Genre(1L, "red");
    public static final Genre GENRE_GREEN = new Genre(2L, "green");
    public static final Genre GENRE_BLUE = new Genre(3L, "blue");

    public static final Book BOOK_CYAN = new Book(1L, "cyan", AUTHOR_1, Set.of(GENRE_GREEN, GENRE_BLUE));
    public static final Book BOOK_MAGENTA = new Book(2L, "magenta", AUTHOR_2, Set.of(GENRE_RED, GENRE_BLUE));
    public static final Book BOOK_YELLOW = new Book(3L, "yellow", AUTHOR_2, Set.of(GENRE_RED, GENRE_GREEN));

    public static final Book NEW_BOOK = new Book(null, "new red book", AUTHOR_1, Set.of(GENRE_RED));

    public static List<Genre> getAllGenres() {
        return Arrays.asList(GENRE_RED, GENRE_GREEN, GENRE_BLUE);
    }
    public static List<Author> getAllAuthors() {
        return Arrays.asList(AUTHOR_1, AUTHOR_2);
    }
    public static List<Book> getAllBooks() {
        return Arrays.asList(BOOK_CYAN, BOOK_MAGENTA, BOOK_YELLOW);
    }
}
