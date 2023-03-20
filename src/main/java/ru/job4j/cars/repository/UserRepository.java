package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author: Egor Bekhterev
 * @date: 08.03.2023
 * @project: job4j_cars
 */
@AllArgsConstructor
public class UserRepository {

    private final CrudRepository crudRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PostRepository.class.getName());

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public Optional<User> create(User user) {
        Optional<User> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(user));
            rsl = Optional.of(user);
        } catch (Exception e) {
            LOGGER.error("Error in the save(User user) method.", e);
        }
        return rsl;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        crudRepository.run("DELETE FROM User WHERE id = :fId", Map.of("fId", userId));
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAll() {
        return crudRepository.query("SELECT DISTINCT i FROM User i ORDER BY i.id", User.class);
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int id) {
        return crudRepository.optional(
                "SELECT DISTINCT i FROM User i WHERE i.id = :fId", User.class, Map.of("fId", id)
        );
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(
                "SELECT DISTINCT i FROM User i WHERE i.login like :fKey", User.class, Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(
                "SELECT DISTINCT i FROM User i WHERE i.login = :fLogin", User.class, Map.of("fLogin", login));
    }
}
