package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.BodyType;

import java.util.List;

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

    public List<BodyType> findAll() {
        return crudRepository.query("SELECT i FROM BodyType i ORDER BY i.id", BodyType.class);
    }
}
