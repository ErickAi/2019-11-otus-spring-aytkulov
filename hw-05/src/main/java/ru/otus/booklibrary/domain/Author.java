package ru.otus.booklibrary.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
public class Author {

    private Integer id;
    private String name;
    private Set<Book> books;

    public Author(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, '%s')", getClass().getSimpleName(), id, name);
    }
}
