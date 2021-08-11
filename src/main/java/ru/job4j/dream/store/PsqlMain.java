package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

public class PsqlMain {
    public static void main(String[] args) {
        //create store
        Store store = PsqlStore.instOf();
        //creat new post
        store.save(new Post(0, "Java senior Job"));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        //update post and show all posts
        store.save(new Post(1, "Intern job"));
        store.findAllPosts().stream().forEach(el -> System.out.println(el.getId() + " " + el.getName()));
        //creat new candidate
        store.save(new Candidate(0, "Java junior"));
        store.findAllCandidates().stream().forEach(el -> System.out.println(el.getId() + " " + el.getName()));
        //update candidate and show all candidates
        store.save(new Candidate(1, "Java middle"));
        store.findAllCandidates().stream().forEach(el -> System.out.println(el.getId() + " " + el.getName()));
        //find by id post and candidate
        Post post = store.findByIdPost(1);
        Candidate candidate = store.findByIdCandidate(1);
        System.out.println(post.getId() + " " + post.getName());
        System.out.println(candidate.getId() + " " + candidate.getName());
    }
}
