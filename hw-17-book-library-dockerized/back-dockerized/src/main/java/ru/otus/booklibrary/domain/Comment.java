package ru.otus.booklibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "entry"})
@Entity
@Table(name = "comments")
public class Comment {

    public Comment(Book book, User user, String entry) {
        this(null, book, user, entry);
    }

    public Comment(Comment comment) {
        this(comment.getId(), comment.getBook(), comment.getUser(),  comment.getEntry());
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob
    @Column(name = "entry")
    @Type(type = "org.hibernate.type.TextType")
    private String entry;
}
