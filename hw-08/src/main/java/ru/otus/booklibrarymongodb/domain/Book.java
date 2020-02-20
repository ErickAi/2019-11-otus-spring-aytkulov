package ru.otus.booklibrarymongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String name;

    private Author author;

    private Set<Genre> genres;

    private Set<Comment> comments;

    public Book(String  id, String name, Author author, Set<Genre> genres) {
        this(id, name, author, genres, null);
    }

    public Set<Genre> getGenres() {
        return genres != null ? genres : new HashSet<>();
    }

    public Set<Comment> getComments() {
        return comments != null ? comments : new HashSet<>();
    }

    @Override
    public String toString() {
        return "Book{(id=" + id + ") \'" + name + '\'' +
                ", author: " + author.getName() +
                ", genres: " + getGenres().stream().map(Genre::getName).collect(Collectors.toList()).toString() +
                '}';
    }
}
