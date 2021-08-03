package ru.job4j.dream.store;

import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Store {
    private static final Store INST = new Store();

    private final Map<Integer, Post> posts = new HashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior java job"));
        posts.put(2, new Post(2, "Middle java job"));
        posts.put(3, new Post(3, "Senior java job"));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}
