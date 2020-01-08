package ru.otus.booklibrary.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.otus.booklibrary.domain.Author;
import ru.otus.booklibrary.domain.Book;
import ru.otus.booklibrary.domain.Genre;
import ru.otus.booklibrary.exception.NotFoundException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class BookDaoImpl implements BookDao {

    private static final BeanPropertyRowMapper<Genre> GENRE_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Genre.class);

    private final NamedParameterJdbcOperations jdbcOperations;
    private final SimpleJdbcInsert jdbcInsert;

    public BookDaoImpl(DataSource dataSource, NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("books")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Integer count() {
        Map<String, Object> params = Collections.emptyMap();
        return jdbcOperations.queryForObject("select count(*) from BOOKS", params, Integer.class);
    }

    @Override
    public Book getById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Book book;
        try {
            book = jdbcOperations.queryForObject(
                    "select book.id, book.name, book.author_id, " +
                            "  author.id , author.name as author_name from books book " +
                            "left outer join authors author " +
                            "  on book.author_id=author.id " +
                            "where book.id= :id"
                    , params, new BookMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("Book with id '%s' not found.", id));
        }
        return setGenres(book);
    }

    @Override
    public Book getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        Book book;
        try {
            book = jdbcOperations.queryForObject(
                    "select book.id, book.name, book.author_id, " +
                            "  author.id , author.name as author_name from books book " +
                            "left outer join authors author " +
                            "  on BOOK.AUTHOR_ID = AUTHOR.ID " +
                            "where BOOK.NAME = :name"
                    , params, new BookMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("Book with name '%s' not found.", name));
        }
        return setGenres(book);
    }

    @Override
    public List<Book> getByAuthor(String author) {
        Map<String, Object> params = Collections.singletonMap("author", author);
        List<Book> books;
        books = jdbcOperations.query(
                "select book.id, book.name, book.author_id, " +
                        "  author.id , author.name as author_name from AUTHORS author " +
                        "left outer join BOOKS book " +
                        "  on BOOK.AUTHOR_ID = AUTHOR.ID " +
                        "where AUTHOR.NAME = :author"
                , params, new RowMapperResultSetExtractor<>(new BookMapper())
        );
        if (books.size() <= 0) {
            throw new NotFoundException(String.format(
                    "Book with author '%s' not found. Perhaps this author does not exist in our library.", author));
        }
        for (Book book : books) {
            setGenres(book);
        }
        return books;
    }

    @Override
    public List<Book> getByGenre(String genre) {
        Map<String, Object> params = Collections.singletonMap("genre", genre);
        List<Book> books;
        books = jdbcOperations.query(
                "SELECT b.id as book_id, B.NAME as book_name, b.AUTHOR_ID, a.NAME as author_name " +
                        "FROM BOOKS b " +
                        "         join BOOK_GENRE on BOOK_GENRE.BOOK_ID = B.ID " +
                        "         join GENRES g on BOOK_GENRE.GENRE_ID = g.ID " +
                        "         join AUTHORS a on b.AUTHOR_ID = a.ID " +
                        "where g.name = :genre"
                , params, new RowMapperResultSetExtractor<>(new BookMapper())
        );
        if (books.size() <= 0) {
            throw new NotFoundException(String.format(
                    "Book with genre '%s' not found. Perhaps this genre does not exist in our library.", genre));
        }
        for (Book book : books) {
            setGenres(book);
        }
        return books;
    }

    @Override
    public List<Book> getAll() {
        Map<Long, Set<Genre>> allGenresByBookId = new HashMap<>();
        jdbcOperations.query("SELECT * FROM BOOK_GENRE " +
                "join GENRES g on BOOK_GENRE.GENRE_ID = g.ID ORDER BY BOOK_ID, GENRE_ID", rs -> {
            allGenresByBookId.compute(rs.getLong("book_id"), (key, value) -> {
                if (value == null) {
                    value = new HashSet<>();
                }
                try {
                    value.add(new Genre(rs.getLong("genre_id"), rs.getString("name")));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return value;
            });
        });
        List<Book> books = jdbcOperations.query("SELECT book.id, book.name, book.author_id, " +
                "author.name as author_name FROM BOOKS as book" +
                " left outer join authors author " +
                "  on book.author_id=author.id", new BookMapper());
        books.forEach(b -> b.setGenres(allGenresByBookId.get(b.getId())));
        return books;
    }

    @Override
    public Book insert(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(
                "authorId", book.getAuthor().getId())
                .addValue("name", book.getName());
        Number newKey = jdbcInsert.executeAndReturnKey(parameterSource);
        book.setId(newKey.longValue());
        insertGenres(book);
        return book;
    }

    @Override
    public boolean deleteById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcOperations.update("DELETE FROM BOOK_GENRE WHERE book_id=:id", params);
        return jdbcOperations.update("delete from BOOKS where id = :id", params) != 0;
    }

    private Book setGenres(Book book) {
        if (book != null) {
            Map<String, Object> params = Collections.singletonMap("book_id", book.getId());
            List<Genre> genres = jdbcOperations.query(
                    "select genre.* from books book " +
                            "  LEFT join book_genre link on book.id=link.BOOK_ID " +
                            "  LEFT join genres genre on link.GENRE_ID=genre.id " +
                            "where book.id= :book_id",
                    params, GENRE_ROW_MAPPER);
            book.setGenres(new HashSet<>(genres));
        }
        return book;
    }

    @SuppressWarnings("unchecked")
    private void insertGenres(Book book) {
        List<Genre> genres = new ArrayList<>(book.getGenres());
        if (!CollectionUtils.isEmpty(genres)) {
            List<Map<String, Object>> parameters = new ArrayList<>();
            for (Genre genre : genres) {
                parameters.add(new HashMap<String, Object>() {{
                    put("bookId", book.getId());
                    put("genreId", genre.getId());
                }});
            }
            Map<String, Object>[] parameterSources = parameters.toArray(new HashMap[0]);
            jdbcOperations.batchUpdate("INSERT INTO BOOK_GENRE (BOOK_ID, GENRE_ID) VALUES (:bookId, :genreId)", parameterSources);
        }
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getInt("id");
            String name = rs.getString("name");
            long authorId = rs.getInt("author_id");
            String authorName = rs.getString("author_name");
            return new Book(id, name, new Author(authorId, authorName));
        }
    }
}
