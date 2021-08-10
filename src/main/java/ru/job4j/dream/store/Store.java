package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidate();

    void save(Post post);

    void save(Candidate candidate);

    Post findByIdPost(int id);

    Candidate findByIdCandidate(int id);
}
