package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {
    private static final MemStore INST = new MemStore();

    private final Map<Integer, Post> posts = new HashMap<>();

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private final Map<Integer, User> users = new HashMap<>();


    private static AtomicInteger POST_ID = new AtomicInteger(4);

    private static AtomicInteger CANDIDATE_ID = new AtomicInteger(4);

    private static AtomicInteger USER_id = new AtomicInteger(0);

    private MemStore() {
        posts.put(1, new Post(1, "Junior java job"));
        posts.put(2, new Post(2, "Middle java job"));
        posts.put(3, new Post(3, "Senior java job"));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    public static MemStore instOf() {
        return INST;
    }

    @Override
   public Collection<Post> findAllPosts() {
        return posts.values();
   }

   @Override
   public Collection<Candidate> findAllCandidates() {
        return candidates.values();
   }

    @Override
    public Collection<User> findAllUsers() {
        return users.values();
    }

    @Override
    public User findByEmail(String email) {
        return users.values().stream().filter(el -> el.getEmail().equals(email)).findFirst().get();
    }

    @Override
   public Post save(Post post) {
       if (post.getId() == 0) {
           post.setId(POST_ID.incrementAndGet());
       }
       posts.put(post.getId(), post);
       return post;
   }

   @Override
   public Candidate save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
        return candidate;
   }

    @Override
    public User save(User user) {
        if (user.getId() == 0) {
            user.setId(USER_id.incrementAndGet());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Post findByIdPost(int id) {
        return posts.get(id);
    }

    @Override
    public Candidate findByIdCandidate(int id) {
        return candidates.get(id);
    }

    @Override
    public User findByIdUser(int id) {
        return users.get(id);
    }

    @Override
    public void removeCandidate(int id) {
        candidates.remove(id);
    }

    @Override
    public void removeUser(int id) {
        users.remove(id);
    }
}
