package ru.otus.springbatch.services;

import java.util.Set;

public interface GenericService<E,T>{

    T transform(E entity);

    T populate(E entity);

    Set<T> populateAll(Set<E> entity);
}
