package ru.otus.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.springbatch.domain.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(BookDao.class)
@DisplayName(value = "DAO для работы с книгами")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class BookDaoImplTest {

    @Autowired
    private BookDaoImpl bookDao;

}