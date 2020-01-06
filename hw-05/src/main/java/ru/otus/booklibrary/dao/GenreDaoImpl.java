package ru.otus.booklibrary.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.otus.booklibrary.domain.Genre;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoImpl implements GenreDao {

    private static final BeanPropertyRowMapper<Genre> GENRE_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Genre.class);

    private final NamedParameterJdbcOperations jdbcOperations;
    private final SimpleJdbcInsert jdbcInsert;

    public GenreDaoImpl(DataSource dataSource, NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("genres")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Integer count() {
        Map<String, Object> params = Collections.emptyMap();
        return jdbcOperations.queryForObject("select count(*) from GENRES", params, Integer.class);
    }

    @Override
    public Genre getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        Genre genre;
        try {
            genre = jdbcOperations.queryForObject(
                    "select genre.id, genre.name from genres genre" +
                            "               where genre.name= :name"
                    , params, GENRE_ROW_MAPPER
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return genre;
    }

    @Override
    public List<Genre> getAll() {
        return jdbcOperations.query("SELECT * FROM GENRES", GENRE_ROW_MAPPER);
    }

    @Override
    public Genre insert(Genre genre) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(genre);
        Number newKey = jdbcInsert.executeAndReturnKey(parameterSource);
        genre.setId(newKey.intValue());
        return genre;
    }
}
