package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.BodyType;

import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 18.03.2023
 * @project: job4j_cars
 */
@ThreadSafe
@Repository
@AllArgsConstructor
public class BodyTypeRepository {

    private CrudRepository crudRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(BodyTypeRepository.class.getName());

    public Optional<BodyType> save(BodyType bodyType) {
        Optional<BodyType> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(bodyType));
            rsl = Optional.of(bodyType);
        } catch (Exception e) {
            LOGGER.error("Error in the save(BodyType bodyType) method.", e);
        }
        return rsl;
    }

    public List<BodyType> findAll() {
        return crudRepository.query("SELECT i FROM BodyType i ORDER BY i.id", BodyType.class);
    }
}
