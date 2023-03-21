package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

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
public class CarService {

    private CarRepository carRepository;

    public Optional<Car> save(Car car) {
        return carRepository.save(car);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }
}
