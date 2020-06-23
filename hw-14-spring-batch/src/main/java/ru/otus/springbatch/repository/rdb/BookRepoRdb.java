package ru.otus.springbatch.repository.rdb;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.springbatch.domain.jpa.BookRdb;

public interface BookRepoRdb extends JpaRepository<BookRdb, Long> {
}
