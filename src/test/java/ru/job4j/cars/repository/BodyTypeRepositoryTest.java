package ru.job4j.cars.repository;

import config.HibernateTestConfiguration;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.BodyType;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class BodyTypeRepositoryTest {

    private final BodyTypeRepository bodyTypeRepository = new BodyTypeRepository(
            new CrudRepository(HibernateTestConfiguration.getSessionFactory()));

    @Test
    public void whenSaveBodyTypesThenAllFound() {
        BodyType bodyType1 = new BodyType();
        BodyType bodyType2 = new BodyType();
        bodyType1.setName("body1");
        bodyType2.setName("body2");
        bodyTypeRepository.save(bodyType1);
        bodyTypeRepository.save(bodyType2);

        var result = bodyTypeRepository.findAll();
        assertThat(result).isEqualTo(List.of(bodyType1, bodyType2));
    }
}
