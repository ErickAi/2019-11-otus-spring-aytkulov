package ru.otus.springbatch.repository.rdb;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.springbatch.domain.jpa.GenreRdb;

public interface GenreRepoRdb extends JpaRepository<GenreRdb, Long> {
}
