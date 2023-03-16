package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;

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

    public List<Car> findAllOrderById() {
        return crudRepository.query(
                "SELECT DISTINCT i FROM Car i JOIN FETCH i.engine JOIN FETCH i.owners ORDER BY i.id", Car.class);
    }
}
