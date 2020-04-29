package ru.otus.booklibrary.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.booklibrary.domain.Genre;
import ru.otus.booklibrary.exception.NotFoundException;

import javax.persistence.*;
import java.util.List;

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
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new NotFoundException(String.format("Book with name '%s' not found.", name));
        }
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
