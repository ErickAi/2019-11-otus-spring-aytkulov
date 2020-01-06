package ru.otus.booklibrary.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
public class Genre implements HasId {

    private Integer id;
    private String name;

    public Genre(String name) {
        this(null, name);
    }

    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, '%s')", getClass().getSimpleName(), id, name);
    }
}
