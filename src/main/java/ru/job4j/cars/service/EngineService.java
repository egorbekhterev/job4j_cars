package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.EngineRepository;

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
public class EngineService {

    private EngineRepository engineRepository;

    public Optional<Engine> save(Engine engine) {
        return engineRepository.save(engine);
    }

    public List<Engine> findAll() {
        return engineRepository.findAll();
    }

    public Optional<Engine> findById(int id) {
        return engineRepository.findById(id);
    }
}
