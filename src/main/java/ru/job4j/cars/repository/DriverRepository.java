package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Driver;

import java.util.List;

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

    public List<Driver> findAllOrderById() {
        return crudRepository.query("SELECT i FROM Driver i JOIN FETCH i.user ORDER BY i.id", Driver.class);
    }
}
