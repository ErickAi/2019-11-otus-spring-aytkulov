package ru.otus.booklibrary.repo;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.booklibrary.domain.Author;
import ru.otus.booklibrary.repo.AuthorDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;

@Repository
@Transactional
@SuppressWarnings("JpaQlInspection")
public class AuthorDaoImpl implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long count() {
        Query query = em.createQuery("select count(a) from Author a");
        return (Long) query.getSingleResult();
    }

    @Override
    public Author getByName(String name) {
        TypedQuery<Author> query = em.createQuery(
            "select a from Author a where a.name = :name", Author.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
    @Override
    public Author insert(Author author) {
        em.persist(author);
        return author;
    }
}
