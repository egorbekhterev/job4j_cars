package ru.job4j.cars.repository;

import config.HibernateTestConfiguration;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class PostRepositoryTest {

    private final CrudRepository crudRepository = new CrudRepository(HibernateTestConfiguration.getSessionFactory());
    private final UserRepository userRepository = new UserRepository(crudRepository);
    private final PostRepository postRepository = new PostRepository(crudRepository);
    private final CarRepository carRepository = new CarRepository(crudRepository);
    private final FileRepository fileRepository = new FileRepository(crudRepository);
    private final BrandRepository brandRepository = new BrandRepository(crudRepository);

    @Test
    public void whenSavePostThenFindAll() {
        Car car = new Car();
        carRepository.save(car);

        User user = new User("user", "password");
        userRepository.create(user);

        Post post = new Post();
        post.setUser(user);
        post.setCar(car);
        postRepository.save(post);

        File file = new File("image", "/path");
        file.setPostId(post.getId());
        fileRepository.save(file);

        var result = postRepository.findAll();
        assertThat(result).isEqualTo(List.of(post));
    }

    @Test
    public void whenSavePostThenFindById() {
        Car car = new Car();
        carRepository.save(car);

        User user = new User("user", "password");
        userRepository.create(user);

        Post post = new Post();
        post.setUser(user);
        post.setCar(car);
        postRepository.save(post);

        var result = postRepository.findById(post.getId()).get();
        assertThat(result).isEqualTo(post);
    }

    @Test
    public void whenSavePostsThenFindItOnTheLastDay() {
        Post post1 = new Post();
        post1.setCreated(LocalDateTime.now().minusHours(5));
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setCreated(LocalDateTime.now().minusDays(2));
        postRepository.save(post2);

        var result = postRepository.findLastDay();
        assertThat(result).isEqualTo(List.of(post1));
    }

    @Test
    public void whenSavePostsAndFindWithSameBrands() {
        Brand brand1 = new Brand();
        brand1.setName("brand");
        brandRepository.save(brand1);
        Brand brand2 = new Brand();
        brand2.setName("anotherBrand");
        brandRepository.save(brand2);

        Car car1 = new Car();
        car1.setBrand(brand1);
        carRepository.save(car1);
        Car car2 = new Car();
        car2.setBrand(brand2);
        carRepository.save(car2);

        Post post1 = new Post();
        post1.setCar(car1);
        postRepository.save(post1);
        Post post2 = new Post();
        post2.setCar(car2);
        postRepository.save(post2);

        var result = postRepository.findSameBrand("brand");
        assertThat(result).isEqualTo(List.of(post1));
    }

    @Test
    public void whenSavePostsWithPhotoThenFindAll() {
        Post post1 = new Post();
        postRepository.save(post1);
        Post post2 = new Post();
        postRepository.save(post2);

        File file = new File("test", "/test");
        file.setPostId(post1.getId());
        fileRepository.save(file);

        var result = postRepository.findWithPhoto();
        assertThat(result).isEqualTo(List.of(post1));
    }
}
