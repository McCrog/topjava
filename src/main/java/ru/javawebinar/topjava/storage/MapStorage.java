package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapStorage implements Storage<Meal> {
    private volatile static int counter = -1;
    private Map<Integer, Meal> storage = new ConcurrentHashMap<>();

    @Override
    public void create(Meal item) {
        item.setId(getNewId());
        storage.put(item.getId(), item);
    }

    @Override
    public Meal read(int id) {
        return storage.get(id);
    }

    @Override
    public void update(Meal item) {
        storage.replace(item.getId(), item);
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
        return new ArrayList<>(storage.values());
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
