package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
}
