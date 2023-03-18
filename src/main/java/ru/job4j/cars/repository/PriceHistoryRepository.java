package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;

/**
 * @author: Egor Bekhterev
 * @date: 18.03.2023
 * @project: job4j_cars
 */
@Repository
@ThreadSafe
@AllArgsConstructor
public class PriceHistoryRepository {

    private CrudRepository crudRepository;

    public List<PriceHistory> findAll() {
        return crudRepository.query(
                "SELECT DISTINCT i FROM PriceHistory i JOIN FETCH i.car JOIN FETCH i.driver ORDER BY i.id",
                PriceHistory.class);
    }
}
