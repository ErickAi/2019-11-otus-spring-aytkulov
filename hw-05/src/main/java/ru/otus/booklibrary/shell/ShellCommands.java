package ru.otus.booklibrary.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.booklibrary.dao.BookDao;
import ru.otus.booklibrary.domain.Book;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final BookDao bookDao;


    @ShellMethod(value = "Get book by id", key = {"b", "book"})
    public void getBookById(int id) {
        System.out.println(bookDao.getById(id).toString());
    }

    @ShellMethod(value = "Get list all books", key = {"all-b", "all-books"})
    public void getAllBooks() {
        List<Book> books = bookDao.getAll();
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }
}
