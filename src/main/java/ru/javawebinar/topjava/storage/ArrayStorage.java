package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class ArrayStorage implements Storage<Meal> {
    private volatile static int counter = -1;
    private List<Meal> storage = new ArrayList<>();

    @Override
    public void create(Meal item) {
        item.setId(ArrayStorage.getNewId());
        storage.add(item);
    }

    @Override
    public Meal read(int id) {
        return storage.get(id);
    }

    @Override
    public void update(Meal item) {
        storage.set(item.getId(), item);
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
        decrementId();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Meal> getAll() {
        return storage;
    }

    @Override
    public int size() {
        return storage.size();
    }

    private synchronized static int getNewId() {
        counter++;
        return counter;
    }

    private synchronized static void decrementId() {
        counter--;
    }
}
