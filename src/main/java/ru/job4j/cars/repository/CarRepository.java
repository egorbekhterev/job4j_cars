package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

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
public class CarRepository {

    private CrudRepository crudRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CarRepository.class.getName());

    public Optional<Car> save(Car car) {
        Optional<Car> rsl = Optional.empty();
        try {
            crudRepository.run(session -> {
                session.persist(car);
                return true;
            });
            rsl = Optional.of(car);
        } catch (Exception e) {
            LOGGER.error("Error in the save(Car car) method.", e);
        }
        return rsl;
    }

    public List<Car> findAll() {
        return crudRepository.query(
                "SELECT DISTINCT i FROM Car i JOIN FETCH i.engine JOIN FETCH i.brand JOIN FETCH i.bodyType "
                        + "JOIN FETCH i.owners ORDER BY i.id", Car.class);
    }

    public Optional<Car> findById(int id) {
        return crudRepository.optional(
                "SELECT DISTINCT i FROM Car i JOIN FETCH i.engine JOIN FETCH i.brand JOIN FETCH i.bodyType "
                        + "JOIN FETCH i.owners WHERE i.id = :fId ORDER BY i.id", Car.class, Map.of("fId", id));
    }
}
