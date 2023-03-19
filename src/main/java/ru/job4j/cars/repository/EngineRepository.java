package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 16.03.2023
 * @project: job4j_cars
 */
@ThreadSafe
@Repository
@AllArgsConstructor
public class EngineRepository {

    private CrudRepository crudRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(EngineRepository.class.getName());

    public Optional<Engine> save(Engine engine) {
        Optional<Engine> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(engine));
            rsl = Optional.of(engine);
        } catch (Exception e) {
            LOGGER.error("Error in the save(Engine engine) method.", e);
        }
        return rsl;
    }

    public List<Engine> findAll() {
        return crudRepository.query("SELECT i FROM Engine i ORDER BY i.id", Engine.class);
    }
}
