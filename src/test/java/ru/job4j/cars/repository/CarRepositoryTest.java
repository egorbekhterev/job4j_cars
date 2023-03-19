package ru.job4j.cars.repository;

import config.HibernateTestConfiguration;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class CarRepositoryTest {

    private final CrudRepository crudRepository = new CrudRepository(HibernateTestConfiguration.getSessionFactory());
    private final CarRepository carRepository = new CarRepository(crudRepository);
    private final EngineRepository engineRepository = new EngineRepository(crudRepository);
    private final BrandRepository brandRepository = new BrandRepository(crudRepository);
    private final BodyTypeRepository bodyTypeRepository = new BodyTypeRepository(crudRepository);

    @Test
    public void whenSaveCarThenFindAll() {
        Engine engine = new Engine();
        engine.setName("engine");
        engineRepository.save(engine);

        Brand brand = new Brand();
        brand.setName("brand");
        brandRepository.save(brand);

        BodyType bodyType = new BodyType();
        bodyType.setName("body");
        bodyTypeRepository.save(bodyType);

        Driver driver = new Driver();

        Car car = new Car();
        car.setName("car");
        car.setEngine(engine);
        car.setBrand(brand);
        car.setBodyType(bodyType);
        car.setOwners(Set.of(driver));
        carRepository.save(car);

        var result = carRepository.findAll();
        assertThat(result).isEqualTo(List.of(car));
    }

    @Test
    public void whenSaveCarThenFindById() {
        Engine engine = new Engine();
        engine.setName("engine");
        engineRepository.save(engine);

        Brand brand = new Brand();
        brand.setName("brand");
        brandRepository.save(brand);

        BodyType bodyType = new BodyType();
        bodyType.setName("body");
        bodyTypeRepository.save(bodyType);

        Driver driver = new Driver();

        Car car = new Car();
        car.setName("car");
        car.setEngine(engine);
        car.setBrand(brand);
        car.setBodyType(bodyType);
        car.setOwners(Set.of(driver));
        carRepository.save(car);

        var result = carRepository.findById(car.getId()).get();
        assertThat(result).isEqualTo(car);
    }
}
