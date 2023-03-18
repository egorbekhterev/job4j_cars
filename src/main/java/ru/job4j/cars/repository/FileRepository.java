package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.File;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 18.03.2023
 * @project: job4j_cars
 */
@Repository
@AllArgsConstructor
@ThreadSafe
public class FileRepository {

    private CrudRepository crudRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PostRepository.class.getName());

    public Optional<File> save(File file) {
        Optional<File> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(file));
            rsl = Optional.of(file);
        } catch (Exception e) {
            LOGGER.error("Error in the save(File file) method.", e);
        }
        return rsl;
    }

    public Optional<File> findById(int id) {
        return crudRepository.optional(
                "SELECT i FROM File i WHERE i.id = :fId", File.class, Map.of("fId", id));
    }

    public List<File> findAll() {
        return crudRepository.query("SELECT i FROM File i ORDER BY i.id", File.class);
    }

    public void deleteById(int id) {
        crudRepository.run("DELETE File WHERE id = :fId", Map.of("fId", id));
    }
}
