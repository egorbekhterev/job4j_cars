package ru.job4j.cars.repository;

import config.HibernateTestConfiguration;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class EngineRepositoryTest {

    private final EngineRepository engineRepository = new EngineRepository(
            new CrudRepository(HibernateTestConfiguration.getSessionFactory()));

    @Test
    public void whenSaveBrandsThenAllFound() {
        Engine engine1 = new Engine();
        Engine engine2 = new Engine();
        engine1.setName("engine1");
        engine2.setName("engine2");
        engineRepository.save(engine1);
        engineRepository.save(engine2);

        var result = engineRepository.findAll();
        assertThat(result).isEqualTo(List.of(engine1, engine2));
    }
}
