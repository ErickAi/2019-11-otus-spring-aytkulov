package ru.otus.booklibrarywebflux.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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

    @DBRef
    private Author author;

    @DBRef
    private Set<Genre> genres;

    public Book(String name, Author author, Set<Genre> genres) {
        this.name = name;
        this.author = author;
        this.genres = genres;
    }
    public Book(Book copy) {
        this.id = copy.getId();
        this.name = copy.getName();
        this.author = copy.getAuthor();
        this.genres = copy.getGenres();
    }

    @Override
    public String toString() {
        return "Book{(id=" + id + ") \'" + name + '\'' +
                ", author: " + author.getName() +
                ", genres: " + getGenres().stream().map(Genre::getName).collect(Collectors.toList()).toString() +
                '}';
    }
}
