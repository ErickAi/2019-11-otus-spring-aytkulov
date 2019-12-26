package ru.otus.booklibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.booklibrary.domain.Author;
import ru.otus.booklibrary.domain.Book;
import ru.otus.booklibrary.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private static final BeanPropertyRowMapper<Genre> GENRE_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Genre.class);

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Book getById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Book book = jdbcOperations.queryForObject(
                "select book.id, book.name, book.author_id, " +
                        "  author.id , author.name as author_name from books book " +
                        "left outer join authors author " +
                        "  on book.author_id=author.id " +
                        "where book.id= :id"
                , params, new BookMapper()
        );
        return setGenres(book);
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public void insert(Book book) {

    }

    @Override
    public void deleteById(int id) {

    }

    private Book setGenres(Book book) {
        if (book != null) {
            Map<String, Object> params = Collections.singletonMap("book_id", book.getId());
            List<Genre> genres = jdbcOperations.query(
                    "select genre.* from books book " +
                            "  LEFT join book_genre link on book.id=link.BOOK_ID " +
                            "  LEFT join genres genre on link.GENRE_ID=genre.id " +
                            "where book.id=1",
                    params, GENRE_ROW_MAPPER);
            book.setGenres(new HashSet<>(genres));
        }
        return book;
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int i) throws SQLException {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int authorId = rs.getInt("author_id");
            String authorName = rs.getString("author_name");
            return new Book(id, name, new Author(authorId, authorName));
        }
    }
}
