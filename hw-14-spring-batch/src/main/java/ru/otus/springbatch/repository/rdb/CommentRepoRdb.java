package ru.otus.springbatch.repository.rdb;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.springbatch.domain.jpa.CommentRdb;

public interface CommentRepoRdb extends JpaRepository<CommentRdb, Long> {
}
