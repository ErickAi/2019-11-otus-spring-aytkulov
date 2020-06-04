package ru.otus.booklibrary.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.booklibrary.domain.Book;
import ru.otus.booklibrary.repo.BookDao;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

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
    @Transactional
    public Book save(Book book) {
        return bookDao.saveOrUpdate(book);
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        return bookDao.deleteById(id);
    }
}
