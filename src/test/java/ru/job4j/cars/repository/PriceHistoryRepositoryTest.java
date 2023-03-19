package ru.job4j.cars.repository;

import config.HibernateTestConfiguration;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class PriceHistoryRepositoryTest {

    private final CrudRepository crudRepository = new CrudRepository(HibernateTestConfiguration.getSessionFactory());
    private final PriceHistoryRepository priceHistoryRepository = new PriceHistoryRepository(crudRepository);

    @Test
    public void whenSavePriceHistoryThenFindAll() {
        PriceHistory priceHistory = new PriceHistory();
        priceHistoryRepository.save(priceHistory);
        var result = priceHistoryRepository.findAll();
        assertThat(result).isEqualTo(List.of(priceHistory));
    }
}
