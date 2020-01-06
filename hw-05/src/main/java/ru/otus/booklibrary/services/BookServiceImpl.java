package ru.otus.booklibrary.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.booklibrary.dao.AuthorDao;
import ru.otus.booklibrary.dao.BookDao;
import ru.otus.booklibrary.dao.GenreDao;
import ru.otus.booklibrary.domain.Book;
import ru.otus.booklibrary.domain.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;


    @Override
    public Book getById(int id) {
        return bookDao.getById(id);
    }

    @Override
    public Book getByName(String name) {
        return bookDao.getByName(name);
    }

    @Override
    public List<Book> getByAuthor(String author) {
        return bookDao.getByAuthor(author);
    }

    @Override
    public List<Book> getByGenre(String genre) {
        return bookDao.getByGenre(genre);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Book save(Book book) {
        if (book.getAuthor().isNew()) {
            book.setAuthor(authorDao.insert(book.getAuthor()));
        }
        Set<Genre> genresWithId = new HashSet<>();
        for (Genre genre : book.getGenres()) {
            if (genre.isNew()) {
                genre = genreDao.insert(genre);
            }
            genresWithId.add(genre);
        }
        book.setGenres(genresWithId);
        return bookDao.insert(book);
    }

    @Override
    public boolean deleteById(int id) {
        return bookDao.deleteById(id);
    }
}
