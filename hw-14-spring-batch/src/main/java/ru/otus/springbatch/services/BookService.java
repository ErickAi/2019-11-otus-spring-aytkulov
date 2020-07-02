package ru.otus.springbatch.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.springbatch.domain.jpa.BookRdb;
import ru.otus.springbatch.domain.nosql.BookMongo;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService implements GenericService<BookRdb, BookMongo> {

    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public BookMongo transform(BookRdb book) {
        return new BookMongo(book.getName(),
                authorService.populate(book.getAuthor()),
                genreService.populateAll(book.getGenres()));
    }

    @Override
    public BookMongo populate(BookRdb entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<BookMongo> populateAll(Set<BookRdb> entity) {
        throw new UnsupportedOperationException();
    }
}
