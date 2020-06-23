package ru.otus.springbatch.domain.nosql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
public class BookMongo {

    private String id;
    private String name;

    @DBRef
    private AuthorMongo author;
    @DBRef
    private Set<GenreMongo> genres;

    public BookMongo(String name, AuthorMongo author, Set<GenreMongo> genres) {
        this(null, name, author, genres);
    }

    @Override
    public String toString() {
        return "Book{(id=" + id + ") \'" + name + '\'' +
                ", author: " + author.getName() +
                ", genres: " + getGenres().stream().map(GenreMongo::getName).collect(Collectors.toList()).toString() +
                '}';
    }
}
