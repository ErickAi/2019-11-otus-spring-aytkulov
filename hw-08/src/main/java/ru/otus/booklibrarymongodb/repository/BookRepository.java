package ru.otus.booklibrarymongodb.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.booklibrarymongodb.domain.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {
}
