package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.PriceHistoryRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 19.03.2023
 * @project: job4j_cars
 */
@Service
@AllArgsConstructor
@ThreadSafe
public class PriceHistoryService {

    private PriceHistoryRepository priceHistoryRepository;

    public Optional<PriceHistory> save(PriceHistory priceHistory) {
        return priceHistoryRepository.save(priceHistory);
    }

    public List<PriceHistory> findAll() {
        return priceHistoryRepository.findAll();
    }

}
