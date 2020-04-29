package ru.otus.booklibrary.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.booklibrary.domain.Author;
import ru.otus.booklibrary.domain.Book;
import ru.otus.booklibrary.domain.Genre;
import ru.otus.booklibrary.exception.NotFoundException;
import ru.otus.booklibrary.repo.AuthorDao;
import ru.otus.booklibrary.repo.GenreDao;
import ru.otus.booklibrary.services.BookService;
import ru.otus.booklibrary.services.IOService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final BookService bookService;
    private final IOService ioService;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;
    private Book newBook;

    @ShellMethod(value = "Get list all books", key = {"b-all", "books-all"})
    public void getAllBooks() {
        List<Book> books = bookService.getAll();
        for (Book book : books) {
            ioService.print(book.toString());
        }
    }

    @ShellMethod(value = "Get book by id", key = {"b", "book"})
    public void getBookById(long id) {
        try {
            ioService.print(bookService.getById(id).toString());
        } catch (Exception e) {
            ioService.print(e.getMessage());
        }
    }

    @ShellMethod(value = "Get list books by author", key = {"b-a", "books-by-author"})
    public void getBooksByAuthor(String author) {
        author = replaceUnderscores(author);
        try {
            List<Book> books = bookService.getByAuthor(author);
            for (Book book : books) {
                ioService.print(book.toString());
            }
        } catch (NotFoundException e) {
            ioService.print(e.getMessage());
        }
    }

    @ShellMethod(value = "Get list books by genre", key = {"b-g", "books-by-genre"})
    public void getBooksByGenre(String genre) {
        genre = replaceUnderscores(genre);
        try {
            List<Book> books = bookService.getByGenre(genre);
            for (Book book : books) {
                ioService.print(book.toString());
            }
        } catch (NotFoundException e) {
            ioService.print(e.getMessage());
        }
    }

    @ShellMethod(value = "Get list all genres", key = {"g-all", "genres-all"})
    public void getAllGenres() {
        List<Genre> genres = genreDao.getAll();
        for (Genre genre : genres) {
            ioService.print(genre.toString());
        }
    }

    @ShellMethod(value = "Create new book command (if name/author/genre contains spaces, replace it to underscores ('_'))"
        , key = {"b-c", "book-create"})
    public void addBook(@ShellOption(value = "--name") String name, @ShellOption("--author") String authorName,
                        @ShellOption(value = "--genre") String genreName) {
        name = replaceUnderscores(name);
        authorName = replaceUnderscores(authorName);
        genreName = replaceUnderscores(genreName);
        Author author = null;
        Genre genre = null;
        try {
            Book book = bookService.getByName(name);
            ioService.print(String.format("The %s exists.", book.toString()));
            ioService.print("Updating book not implemented yet");
            author = authorDao.getByName(authorName);
            genre = genreDao.getByName(genreName);
        } catch (NotFoundException ignored) {
        }
        if (author == null) {
            ioService.print(String.format("Author with name '%s' is not exists. A new one will be created.", authorName));
            author = new Author(authorName);
        }
        if (genre == null) {
            ioService.print(String.format("Genre with name '%s' is not exists. A new one will be created.", genreName));
            genre = new Genre(genreName);
        }
        newBook = new Book(null, name, author, new HashSet<>(Collections.singleton(genre)));
        ioService.print(String.format("%s ready to save.", newBook));
        ioService.print("Now you can add genres to created new book and/or save/deny creation");
    }

    @ShellMethod(value = "Add genre to new book", key = {"g-add", "genre-add-to-new-book"})
    public void addGenreToNewBook(String genreName) {
        genreName = replaceUnderscores(genreName);
        Genre genre = genreDao.getByName(genreName);
        if (genre == null) {
            ioService.print(String.format("Genre with name '%s' is not exists. A new one will be created.", genreName));
            genre = new Genre(genreName);
        }
        newBook.getGenres().add(genre);
        ioService.print(String.format("%s ready to save.", newBook));
    }

    @ShellMethod(value = "Delete book by ID", key = {"b-d", "book-delete"})
    public void deleteBookById(long id) {
        if (bookService.deleteById(id)) {
            ioService.print(String.format("book with id '%s' deleted.", id));
        }
    }

    private String replaceUnderscores(String option) {
        return option.replace('_', ' ');
    }

    @ShellMethod(value = "Save creation command", key = {"bcs", "save-creation"})
    public void saveCreation() {
        ioService.print(bookService.save(newBook).toString() + " saved.");
        newBook = null;
    }

    @ShellMethod(value = "Deny creation command", key = {"bcd", "deny-creation"})
    public void denyCreation() {
        newBook = null;
    }

    private Availability getAllBooksAvailability() {
        return creationAvailability();
    }

    private Availability getBookByIdAvailability() {
        return creationAvailability();
    }

    private Availability getBooksByAuthorAvailability() {
        return creationAvailability();
    }

    private Availability getBooksByGenreAvailability() {
        return creationAvailability();
    }

    private Availability deleteBookByIdAvailability() {
        return creationAvailability();
    }

    private Availability addGenreToNewBookAvailability() {
        return saveOrDenyAvailability();
    }

    private Availability saveCreationAvailability() {
        return saveOrDenyAvailability();
    }

    private Availability denyCreationAvailability() {
        return saveOrDenyAvailability();
    }

    private Availability creationAvailability() {
        if (newBook != null) {
            return Availability.unavailable("\n Now you creating the book %s. \nConfirm or deny creation by command " +
                "\n Confirm: 'c', 'confirm-creation'. \n Deny: 'd', 'deny-creation'");
        }
        return Availability.available();
    }

    private Availability saveOrDenyAvailability() {
        if (newBook == null) {
            return Availability.unavailable("\n This command for save or deny created book." +
                "\nFor create new book use command ");
        }
        return Availability.available();
    }
}
