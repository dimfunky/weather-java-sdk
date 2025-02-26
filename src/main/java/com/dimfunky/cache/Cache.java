package com.dimfunky.cache;

public interface Cache<T> {
    T get(String key);

    void put(String key, T value);

    boolean contains(String key);

    int size();

    void clear();

    Object getAll();
}
