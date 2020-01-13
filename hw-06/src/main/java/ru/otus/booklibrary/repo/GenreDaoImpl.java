package ru.otus.booklibrary.repo;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.booklibrary.domain.Author;
import ru.otus.booklibrary.domain.Genre;
import ru.otus.booklibrary.repo.GenreDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@SuppressWarnings("JpaQlInspection")
public class GenreDaoImpl implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long count() {
        Query query = em.createQuery("select count(g) from Genre g");
        return (Long) query.getSingleResult();
    }

    @Override
    public Genre getByName(String name) {
        TypedQuery<Genre> query = em.createQuery(
            "select g from Genre g where g.name = :name", Genre.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<Genre> getAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public Genre insert(Genre genre) {
        em.persist(genre);
        return genre;
    }
}
