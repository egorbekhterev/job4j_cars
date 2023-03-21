package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.repository.DriverRepository;

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
public class DriverService {

    private DriverRepository driverRepository;

    public Optional<Driver> save(Driver driver) {
        return driverRepository.save(driver);
    }

    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    public Optional<Driver> findById(int id) {
        return driverRepository.findById(id);
    }
}
