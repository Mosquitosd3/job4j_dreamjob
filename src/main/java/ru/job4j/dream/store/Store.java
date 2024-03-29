package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    Collection<User> findAllUsers();

    User findByEmail(String email);

    Post save(Post post);

    Candidate save(Candidate candidate);

    User save(User user);

    Post findByIdPost(int id);

    Candidate findByIdCandidate(int id);

    User findByIdUser(int id);

    void removeCandidate(int id );

    void removeUser(int id);
}
