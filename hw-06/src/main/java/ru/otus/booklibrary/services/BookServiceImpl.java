package ru.otus.booklibrary.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.booklibrary.domain.Book;
import ru.otus.booklibrary.domain.Genre;
import ru.otus.booklibrary.repo.AuthorDao;
import ru.otus.booklibrary.repo.BookDao;
import ru.otus.booklibrary.repo.GenreDao;

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
    public Book getById(long id) {
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
        if (book.getAuthor().getId() == null) {
            book.setAuthor(authorDao.insert(book.getAuthor()));
        }
        Set<Genre> genres = new HashSet<>();
        for (Genre genre : book.getGenres()) {
            if (genre.getId() == null) {
                genreDao.insert(genre);
            }
            genres.add(genre);
        }
        book.setGenres(genres);
        return bookDao.saveOrUpdate(book);
    }

    @Override
    public boolean deleteById(long id) {
        return bookDao.deleteById(id);
    }
}
