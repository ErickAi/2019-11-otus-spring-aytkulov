package ru.otus.booklibrarymongodb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.booklibrarymongodb.domain.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public Book getById(Long id) {
        return null;
    }

    @Override
    public Book getByName(String name) {
        return null;
    }

    @Override
    public List<Book> getAllContainsAuthorName(String authorName) {
        return null;
    }

    @Override
    public List<Book> findByGenre(String genreName) {
        return null;
    }

    @Override
    @Transactional
    public Book save(Book book) {
        return null;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
    }

    @Override
    @Transactional
    public void addCommentToBook(Long bookId, String comment) {
    }
}
