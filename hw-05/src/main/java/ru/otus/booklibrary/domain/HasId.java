package ru.otus.booklibrary.domain;

public interface HasId {
    Long getId();

    void setId(Long id);

    default boolean isNew() {
        return getId() == null;
    }
}
