package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 17.03.2023
 * @project: job4j_cars
 */
@Repository
@AllArgsConstructor
@ThreadSafe
public class PostRepository {

    private CrudRepository crudRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PostRepository.class.getName());

    public List<Post> findLastDay() {
        var minusDay = LocalDateTime.now().minusDays(1);
        return crudRepository.query(
                "SELECT i FROM Post i WHERE i.created >= :fMinusDay ORDER BY i.id",
                Post.class, Map.of("fMinusDay", minusDay));
    }

    public List<Post> findWithPhoto() {
        return crudRepository.query(
                "SELECT i FROM Post i WHERE i.photo IS NOT NULL ORDER BY i.id", Post.class);
    }

    public List<Post> findSameBrand(String brand) {
        return crudRepository.query(
                "SELECT i FROM Post i JOIN FETCH i.car WHERE i.car.name = :fName ORDER BY i.id",
                Post.class, Map.of("fName", brand));
    }

    public List<Post> findAll() {
        return crudRepository.query(
                "SELECT DISTINCT i FROM Post i JOIN FETCH i.auto_user JOIN FETCH i.car ORDER BY i.id",
                Post.class);
    }

    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                "SELECT DISTINCT i FROM Post i JOIN FETCH i.auto_user JOIN FETCH i.car WHERE i.id = :fId",
                Post.class, Map.of("fId", id));
    }

    public Optional<Post> save(Post post) {
        Optional<Post> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(post));
            rsl = Optional.of(post);
        } catch (Exception e) {
            LOGGER.error("Error in the save(Post post) method.", e);
        }
        return rsl;
    }
}
