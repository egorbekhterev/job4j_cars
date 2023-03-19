package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 16.03.2023
 * @project: job4j_cars
 */
@ThreadSafe
@Repository
@AllArgsConstructor
public class DriverRepository {

    private CrudRepository crudRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(Driver.class.getName());

    public List<Driver> findAll() {
        return crudRepository.query("SELECT i FROM Driver i JOIN FETCH i.user ORDER BY i.id", Driver.class);
    }

    public Optional<Driver> save(Driver driver) {
        Optional<Driver> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(driver));
            rsl = Optional.of(driver);
        } catch (Exception e) {
            LOGGER.error("Error in the save(Driver driver) method.", e);
        }
        return rsl;
    }

    public Optional<Driver> findById(int id) {
        return crudRepository.optional(
                "SELECT i FROM Driver i JOIN FETCH i.user WHERE i.id = :fId", Driver.class, Map.of("fId", id));
    }
}
