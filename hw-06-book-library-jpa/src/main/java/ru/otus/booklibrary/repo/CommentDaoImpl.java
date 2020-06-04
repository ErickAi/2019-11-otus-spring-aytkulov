package ru.otus.booklibrary.repo;

import org.springframework.stereotype.Repository;
import ru.otus.booklibrary.domain.Comment;
import ru.otus.booklibrary.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@SuppressWarnings("JpaQlInspection")
public class CommentDaoImpl implements CommentDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> getAllByBookId(Long bookId) {
        TypedQuery<Comment> query = em.createQuery(
                "select c from Comment c join c.book book where book.id = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        List<Comment> comments = query.getResultList();
        if (comments.isEmpty()) {
            throw new NotFoundException("Not found comments for book with id: " + bookId);
        }
        return comments;
    }
}
