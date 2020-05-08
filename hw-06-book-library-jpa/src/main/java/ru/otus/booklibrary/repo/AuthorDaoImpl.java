package ru.otus.booklibrary.repo;

import org.springframework.stereotype.Repository;
import ru.otus.booklibrary.domain.Author;
import ru.otus.booklibrary.exception.NotFoundException;

import javax.persistence.*;

@Repository
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
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new NotFoundException(String.format("Book with name '%s' not found.", name));
        }
    }
    @Override
    public Author insert(Author author) {
        em.persist(author);
        return author;
    }
}
