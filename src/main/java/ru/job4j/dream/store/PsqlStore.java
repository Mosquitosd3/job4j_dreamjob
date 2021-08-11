package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
public class PsqlStore implements Store {
    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        ClassLoader loader = this.getClass().getClassLoader();
        try (InputStream io = loader.getResourceAsStream("bd.properties")) {
            cfg.load(io);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
//        try (BufferedReader io = new BufferedReader(new FileReader("bd.properties"))) {
//            cfg.load(io);
//        } catch (Exception e) {
//          throw new IllegalStateException(e);
//        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while(it.next()) {
                    candidates.add(new Candidate(it.getInt("id"), it.getString("name")));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0 ) {
            createPost(post);
        } else {
            update(post);
        }
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            creatCandidate(candidate);
        } else {
            update(candidate);
        }
    }

    private Post createPost(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO post(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private Candidate creatCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO candidate(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
           ps.setString(1, candidate.getName());
           ps.execute();
           try (ResultSet id = ps.getGeneratedKeys()) {
               if (id.next()) {
                   candidate.setId(id.getInt(1));
               }
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    private void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE post p SET name = ? WHERE p.id = ?")
        ) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE candidate c SET name ? WHERE c.id = ?")
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post findByIdPost(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post p WHERE p.id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet result = ps.executeQuery()) {
                if (result.next()) {
                    post = new Post(
                            result.getInt("id"),
                            result.getString("name")
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Candidate findByIdCandidate(int id) {
        Candidate candidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate c WHERE c.id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet result = ps.executeQuery()) {
                if (result.next()) {
                    candidate = new Candidate(
                            result.getInt("id"),
                            result.getString("name")
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    @Override
    public void removeCandidate(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM candidate c WHERE c.id = ?")
        ) {
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
