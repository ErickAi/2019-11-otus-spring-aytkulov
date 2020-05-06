package ru.otus.booklibrary.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.booklibrary.domain.Book;
import ru.otus.booklibrary.domain.Comment;
import ru.otus.booklibrary.exception.NotFoundException;
import ru.otus.booklibrary.repo.BookRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    @Override
    public List<Book> getAll() {
        return bookRepo.findAll();
    }

    @Override
    public Book getById(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id '" + id + "' not found."));
        Hibernate.initialize(book.getComments());
        return book;
    }

    @Override
    public Book getByName(String name) {
        return bookRepo.getByNameIgnoreCase(name)
                .orElseThrow(() -> new NotFoundException("Book with name '" + name + "' not found."));
    }

    @Override
    public List<Book> getAllContainsAuthorName(String authorName) {
        return bookRepo.getAllByAuthorNameContainsIgnoreCase(authorName);
    }

    @Override
    public List<Book> findByGenre(String genreName) {
        return bookRepo.findByGenres_NameIgnoreCase(genreName);
    }

    @Override
    @Transactional
    public Book save(Book book) {
        return bookRepo.save(book);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        bookRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void addCommentToBook(Long bookId, String comment) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id '" + bookId + "' not found."));
        book.getComments().add(new Comment(comment, book));
        bookRepo.save(book);
    }
}
