package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore {
    private static final MemStore INST = new MemStore();

    private final Map<Integer, Post> posts = new HashMap<>();

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private static AtomicInteger POST_ID = new AtomicInteger(4);

    private static AtomicInteger CANDIDATE_ID = new AtomicInteger(4);

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

   public Collection<Post> findAllPost() {
        return posts.values();
   }

   public Collection<Candidate> findAllCandidates() {
        return candidates.values();
   }

   public void save(Post post) {
       if (post.getId() == 0) {
           post.setId(POST_ID.incrementAndGet());
       }
       posts.put(post.getId(), post);
   }

   public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
   }

    public Post findByIdPost(int id) {
        return posts.get(id);
    }

    public Candidate findByIdCandidate(int id) {
        return candidates.get(id);
    }

    public void removeCandidate(int id) {
        candidates.remove(id);
    }
}
