package ru.otus.booklibrary;

import ru.otus.booklibrary.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class TestData {
    // identical with test-data.sql
    public static final User ADMIN = new User(1L, "Admin Admin", "a@a.a");
    public static final User USER = new User(2L, "User User", "u@u.u");

    public static final Author AUTHOR_YELLOW = new Author(1L, "yellow book author");
    public static final Author AUTHOR_MAGENTA_CYAN = new Author(2L, "magenta & cyan book author");

    public static final Genre GENRE_RED = new Genre(1L, "red");
    public static final Genre GENRE_GREEN = new Genre(2L, "green");
    public static final Genre GENRE_BLUE = new Genre(3L, "blue");

    public static final Book BOOK_YELLOW = new Book(1L, "yellow", AUTHOR_YELLOW, Set.of(GENRE_RED, GENRE_GREEN));
    public static final Book BOOK_MAGENTA = new Book(2L, "magenta", AUTHOR_MAGENTA_CYAN, Set.of(GENRE_RED, GENRE_BLUE));
    public static final Book BOOK_CYAN = new Book(3L, "cyan", AUTHOR_MAGENTA_CYAN, Set.of(GENRE_GREEN, GENRE_BLUE));

    public static final Comment ADMIN_YELLOW_COMMENT = new Comment(1L, BOOK_YELLOW, ADMIN, "admin comment for yellow");
    public static final Comment USER_YELLOW_COMMENT = new Comment(2L, BOOK_YELLOW, USER,"user comment for yellow");

    // new data
    public static final Book NEW_BOOK = new Book(null, "new red book", AUTHOR_YELLOW, Set.of(GENRE_RED));
    public static final Comment NEW_COMMENT = new Comment(null, BOOK_CYAN, USER, "new cyan-book comment");

    // getters
    public static List<Genre> getAllGenres() {
        return Arrays.asList(GENRE_RED, GENRE_GREEN, GENRE_BLUE);
    }
    public static List<Author> getAllAuthors() {
        return Arrays.asList(AUTHOR_YELLOW, AUTHOR_MAGENTA_CYAN);
    }
    public static List<Book> getAllBooks() {
        return Arrays.asList(BOOK_CYAN, BOOK_MAGENTA, BOOK_YELLOW);
    }
}
