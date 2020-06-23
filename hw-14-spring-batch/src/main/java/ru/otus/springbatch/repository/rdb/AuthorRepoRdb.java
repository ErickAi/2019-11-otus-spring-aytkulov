package ru.otus.springbatch.repository.rdb;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.springbatch.domain.jpa.AuthorRdb;

public interface AuthorRepoRdb extends JpaRepository<AuthorRdb, Long> {
}
