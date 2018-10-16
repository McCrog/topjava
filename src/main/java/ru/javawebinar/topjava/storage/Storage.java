package ru.javawebinar.topjava.storage;

import java.util.List;

public interface Storage<T> {

    void create(T item);

    T read(int id);

    void update(T item);

    void delete(int id);

    void clear();

    List<T> getAll();

    int size();
}
