package ru.job4j.cars.repository;

import config.HibernateTestConfiguration;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class UserRepositoryTest {

    private final UserRepository userRepository = new UserRepository(new CrudRepository(
            HibernateTestConfiguration.getSessionFactory()));

    @Test
    public void whenCreateUserThenFindItById() {
        User user = new User("user", "password");
        userRepository.create(user);

        var result = userRepository.findById(user.getId());
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    public void whenCreateUsersThenFindAll() {
        User user1 = new User("user1", "password");
        User user2 = new User("user2", "password");
        userRepository.create(user1);
        userRepository.create(user2);

        var result = userRepository.findAll();
        assertThat(result).isEqualTo(List.of(user1, user2));
    }

    @Test
    public void whenUpdateUserThenFindItById() {
        User user = new User("user", "password");
        userRepository.create(user);
        user.setLogin("updatedUser");
        user.setPassword("updatedPassword");
        userRepository.update(user);

        var result = userRepository.findById(user.getId()).get();
        assertThat(result.getLogin()).isEqualTo("updatedUser");
        assertThat(result.getPassword()).isEqualTo("updatedPassword");
    }

    @Test
    public void whenDeleteThenGetEmpty() {
        User user = new User("user", "password");
        userRepository.create(user);
        userRepository.delete(user.getId());
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    public void whenFindByLikeLoginThenGetUsers() {
        User user1 = new User("user1", "password");
        User user2 = new User("user2", "password");
        User user3 = new User("qwerty", "password");

        userRepository.create(user1);
        userRepository.create(user2);
        userRepository.create(user3);

        var result = userRepository.findByLikeLogin("user");
        assertThat(result).isEqualTo(List.of(user1, user2));
    }

    @Test
    public void whenFindByLoginThenGetUser() {
        User user1 = new User("user1", "password");
        User user2 = new User("user2", "password");
        User user3 = new User("qwerty", "password");

        userRepository.create(user1);
        userRepository.create(user2);
        userRepository.create(user3);

        var result = userRepository.findByLogin("user2").get();
        assertThat(result).isEqualTo(user2);
    }

    @Test
    public void whenSavingUserWhoAlreadyExists() {
        User user1 = new User("user1", "password");
        userRepository.create(user1);

        User user2 = new User("user1", "password");
        userRepository.create(user2);

        var result = userRepository.findAll();
        assertThat(result).isEqualTo(List.of(user1));
    }
}
