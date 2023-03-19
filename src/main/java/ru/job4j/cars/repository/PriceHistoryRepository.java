package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Optional;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceHistoryRepository.class.getName());

    public Optional<PriceHistory> save(PriceHistory priceHistory) {
        Optional<PriceHistory> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(priceHistory));
            rsl = Optional.of(priceHistory);
        } catch (Exception e) {
            LOGGER.error("Error in the save(PriceHistory priceHistory) method.", e);
        }
        return rsl;
    }

    public List<PriceHistory> findAll() {
        return crudRepository.query(
                "SELECT DISTINCT i FROM PriceHistory i ORDER BY i.id",
                PriceHistory.class);
    }
}
