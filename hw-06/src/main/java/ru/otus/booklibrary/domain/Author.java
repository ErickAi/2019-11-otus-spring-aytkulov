package ru.otus.booklibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Author(String name) {
        this(null, name);
    }

    @Override
    public String toString() {
        return String.format("%s (%s, '%s')", getClass().getSimpleName(), id, name);
    }
}
