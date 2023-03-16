package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;

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

    public List<Engine> findAllOrderById() {
        return crudRepository.query("SELECT i FROM Engine i ORDER BY i.id", Engine.class);
    }
}
