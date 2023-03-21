package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 18.03.2023
 * @project: job4j_cars
 */
@ThreadSafe
@Repository
@AllArgsConstructor
public class BrandRepository {

    private CrudRepository crudRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandRepository.class.getName());

    public Optional<Brand> save(Brand brand) {
        Optional<Brand> rsl = Optional.empty();
        try {
            crudRepository.run(session -> {
                session.persist(brand);
                return true;
            });
            rsl = Optional.of(brand);
        } catch (Exception e) {
            LOGGER.error("Error in the save(Brand brand) method.", e);
        }
        return rsl;
    }

    public List<Brand> findAll() {
        return crudRepository.query("SELECT i FROM Brand i ORDER BY i.id", Brand.class);
    }

    public Optional<Brand> findById(int id) {
        return crudRepository.optional(
                "SELECT i FROM Brand i WHERE i.id = :fId", Brand.class, Map.of("fId", id)
        );
    }
}
