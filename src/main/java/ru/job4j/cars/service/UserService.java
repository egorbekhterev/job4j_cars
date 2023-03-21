package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.UserRepository;

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
public class UserService {

    private UserRepository userRepository;

    public Optional<User> create(User user) {
        return userRepository.create(user);
    }

    public boolean update(User user) {
        return userRepository.update(user);
    }

    public boolean delete(int userId) {
        return userRepository.delete(userId);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public List<User> findByLikeLogin(String key) {
        return userRepository.findByLikeLogin(key);
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }
}
