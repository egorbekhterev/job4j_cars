package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Brand;

import java.util.List;

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

    public List<Brand> findAll() {
        return crudRepository.query("SELECT i FROM Brand i ORDER BY i.id", Brand.class);
    }
}
