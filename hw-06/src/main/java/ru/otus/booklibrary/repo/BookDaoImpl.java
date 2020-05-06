package ru.otus.booklibrary.repo;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.booklibrary.domain.Book;
import ru.otus.booklibrary.exception.NotFoundException;

import javax.persistence.*;
import java.util.List;

@Repository
@Transactional
@SuppressWarnings("JpaQlInspection")
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Long count() {
        return (Long) em.createQuery("select count(a) from Book a").getSingleResult();
    }

    @Override
    public Book getById(Long id) {
        TypedQuery<Book> query = em.createQuery(
            "select b from Book b where b.id = :id"
            , Book.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new NotFoundException(String.format("Book with id '%s' not found.", id));
        }
    }

    @Override
    public Book getByName(String name) {
        TypedQuery<Book> query = em.createQuery(
            "select b from Book b where b.name = :name", Book.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new NotFoundException(String.format("Book with name '%s' not found.", name));
        }
    }

    @Override
    public List<Book> getByAuthor(String author) {
        TypedQuery<Book> query = em.createQuery(
            "select b from Book b join b.author author where author.name = :authorName", Book.class);
        query.setParameter("authorName", author);
        List<Book> books = query.getResultList();
        if (books.isEmpty()) {
            throw new NotFoundException(String.format(
                "Book with author '%s' not found. Perhaps this author does not exist in our library.", author));
        }
        return books;
    }

    @Override
    public List<Book> getByGenre(String genre) {
        TypedQuery<Book> query = em.createQuery(
            "select b from Book b join b.genres genre where genre.name = :genreName", Book.class);
        query.setParameter("genreName", genre);
        List<Book> books = query.getResultList();
        if (books.isEmpty()) {
            throw new NotFoundException(String.format(
                "Book with genre '%s' not found. Perhaps this genre does not exist in our library.", genre));
        }
        return books;
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public Book saveOrUpdate(Book book) {
        Session session = em.unwrap(Session.class);
        session.saveOrUpdate(book);
        return book;
    }

    @Override
    public boolean deleteById(long id) {
        Query deleteComments = em.createQuery("delete from Comment c where c.bookId =:id");
        deleteComments.setParameter("id", id);
        deleteComments.executeUpdate();
        Query query = em.createQuery("delete from Book b where b.id =:id");
        query.setParameter("id", id);
        return query.executeUpdate() == 1;
    }
}
