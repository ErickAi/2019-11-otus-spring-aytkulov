package ru.otus.booklibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "book_genre", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;

    public Book(Long id, String name, Author author) {
        this(id, name, author, null);
        this.name = name;
        this.author = author;
    }

    public Set<Genre> getGenres() {
        return genres != null ? genres : new HashSet<>();
    }

    @Override
    public String toString() {
        return "Book{(id=" + id + ") \'" + name + '\'' +
                ", author: " + author.getName() +
                ", genres: " + getGenres().stream().map(Genre::getName).collect(Collectors.toList()).toString() +
                '}';
    }
}
