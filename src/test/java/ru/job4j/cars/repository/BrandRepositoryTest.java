package ru.job4j.cars.repository;

import config.HibernateTestConfiguration;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Brand;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class BrandRepositoryTest {

    private final BrandRepository brandRepository = new BrandRepository(
            new CrudRepository(HibernateTestConfiguration.getSessionFactory()));

    @Test
    public void whenSaveBrandsThenAllFound() {
        Brand brand1 = new Brand();
        Brand brand2 = new Brand();
        brand1.setName("brand1");
        brand2.setName("brand2");
        brandRepository.save(brand1);
        brandRepository.save(brand2);

        var result = brandRepository.findAll();
        assertThat(result).isEqualTo(List.of(brand1, brand2));
    }
}
