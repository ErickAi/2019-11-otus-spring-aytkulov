package ru.otus.booklibrary.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
public class Book implements HasId {

    private Long id;
    private String name;
    private Author author;
    private Set<Genre> genres;

    public Book(Long id, String name, Author author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public Book(Long id, String name, Author author, Set<Genre> genres) {
        this(id, name, author);
        this.genres = genres;
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
