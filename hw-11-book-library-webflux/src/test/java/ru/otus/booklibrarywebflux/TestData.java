package ru.otus.booklibrarywebflux;

import ru.otus.booklibrarywebflux.domain.Author;
import ru.otus.booklibrarywebflux.domain.Book;
import ru.otus.booklibrarywebflux.domain.Comment;
import ru.otus.booklibrarywebflux.domain.Genre;

import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class TestData {

    public static final String NEW_NAME = "New name";
    public static final Author DOSTOEVSKIY = new Author("1", "Федор Достоевский");
    public static final Author HOMER = new Author("2", "Гомер");

    public static final Genre CLASSIC = new Genre("1", "Классика");
    public static final Genre PROSE = new Genre("2", "Проза");
    public static final Genre POEM = new Genre("3", "Поэма");

    public static final Book IDIOT = new Book("1", "Идиот", DOSTOEVSKIY, Set.of(CLASSIC, PROSE));
    public static final Book ILIAD = new Book("2", "Илиада", HOMER, Set.of(CLASSIC, POEM));
    public static final Book ODYSSEY = new Book("3", "Одиссея", HOMER, Set.of(CLASSIC, POEM));
    public static final Book DEMONS = new Book("Demons", DOSTOEVSKIY, Set.of(CLASSIC, PROSE));

    public static final Comment IDIOT_COMMENT_1 = new Comment("1", "1", "Первый комментарий к книге 'Идиот'");
    public static final Comment IDIOT_COMMENT_2 = new Comment("2", "1", "Второй комментарий к книге 'Идиот'");
    public static final Comment ILIAD_COMMENT = new Comment("3", "2", "Комментарий к книге 'Илиада'");
    public static final int IDIOT_COMMENTS_COUNT = 2;

    public static Set<Author> getAuthors(){
        return Set.of(DOSTOEVSKIY, HOMER);
    }
    public static Set<Genre> getGenres(){
        return Set.of(CLASSIC, PROSE, POEM);
    }
    public static Set<Book> getBooks(){
        return Set.of(IDIOT, ILIAD, ODYSSEY);
    }

    public static Set<Comment> getComments() {
        return Set.of(IDIOT_COMMENT_1, IDIOT_COMMENT_2, ILIAD_COMMENT);
    }
}
