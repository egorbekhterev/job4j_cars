package ru.job4j.cars.repository;

import config.HibernateTestConfiguration;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class DriverRepositoryTest {

    private final CrudRepository crudRepository = new CrudRepository(HibernateTestConfiguration.getSessionFactory());
    private final UserRepository userRepository = new UserRepository(crudRepository);
    private final DriverRepository driverRepository = new DriverRepository(crudRepository);

    @Test
    public void whenFindByIdThenGetDriverAndUserField() {
        User user = new User("user", "password");
        Driver driver = new Driver();
        driver.setName("driver");
        driver.setUser(user);
        userRepository.create(user);
        driverRepository.save(driver);

        var result = driverRepository.findById(driver.getId()).get();
        assertThat(result).isEqualTo(driver);
        assertThat(result.getUser()).isEqualTo(user);
    }

    @Test
    public void whenSaveTwoDriversWithCommonUserThenGetEmpty() {
        User user = new User("user", "password");
        userRepository.create(user);

        Driver driver1 = new Driver();
        Driver driver2 = new Driver();
        driver1.setName("driver1");
        driver2.setName("driver2");
        driver1.setUser(user);
        driver2.setUser(user);

        driverRepository.save(driver1);
        var rsl = driverRepository.save(driver2);

        assertThat(rsl).isEmpty();
    }

    @Test
        public void whenCreateDriversThenFindAll() {
        User user1 = new User("user1", "password");
        User user2 = new User("user2", "password");
        Driver driver1 = new Driver();
        Driver driver2 = new Driver();

        driver1.setName("driver1");
        driver2.setName("driver2");
        driver1.setUser(user1);
        driver2.setUser(user2);

        userRepository.create(user1);
        userRepository.create(user2);
        driverRepository.save(driver1);
        driverRepository.save(driver2);

        var result = driverRepository.findAll();
        assertThat(result).isEqualTo(List.of(driver1, driver2));
    }
}
