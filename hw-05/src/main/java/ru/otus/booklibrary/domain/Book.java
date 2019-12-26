package ru.otus.booklibrary.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
public class Book {

    private Integer id;
    private String name;
    private Author author;
    private Set<Genre> genres;

    public Book(Integer id, String name, Author author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{(id=" + id + ") \'" + name + '\'' +
                ", author: " + author.getName() +
                ", genres: " + genres.stream().map(Genre::getName).collect(Collectors.toList()).toString() +
                '}';
    }
}
