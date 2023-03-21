package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.BodyType;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.BodyTypeRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 19.03.2023
 * @project: job4j_cars
 */
@Service
@ThreadSafe
@AllArgsConstructor
public class BodyTypeService {

    private BodyTypeRepository bodyTypeRepository;

    public Optional<BodyType> save(BodyType bodyType) {
        return bodyTypeRepository.save(bodyType);
    }

    public List<BodyType> findAll() {
        return bodyTypeRepository.findAll();
    }

    public Optional<BodyType> findById(int id) {
        return bodyTypeRepository.findById(id);
    }
}
