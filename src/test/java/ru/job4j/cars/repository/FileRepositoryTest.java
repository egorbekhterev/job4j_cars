package ru.job4j.cars.repository;

import config.HibernateTestConfiguration;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Post;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class FileRepositoryTest {

    private final CrudRepository crudRepository = new CrudRepository(HibernateTestConfiguration.getSessionFactory());
    private final FileRepository fileRepository = new FileRepository(crudRepository);
    private final PostRepository postRepository = new PostRepository(crudRepository);

    @Test
    public void whenSaveFileThenGetItById() {
        Post post = new Post();
        postRepository.save(post);

        File file = new File("test", "/test");
        file.setPostId(post.getId());
        fileRepository.save(file);

        var result = fileRepository.findById(file.getId());
        assertThat(result.get()).isEqualTo(file);
    }

    @Test
    public void whenSaveFilesThenAllFound() {
        Post post1 = new Post();
        postRepository.save(post1);
        Post post2 = new Post();
        postRepository.save(post2);

        File file1 = new File("test1", "/test1");
        File file2 = new File("test2", "/test2");
        file1.setPostId(post1.getId());
        file2.setPostId(post2.getId());
        fileRepository.save(file1);
        fileRepository.save(file2);

        var result = fileRepository.findAll();
        assertThat(result).isEqualTo(List.of(file1, file2));
    }

    @Test
    public void whenDeleteFileThenItIsEmpty() {
        Post post = new Post();
        postRepository.save(post);

        File file = new File("test", "/test");
        file.setPostId(post.getId());
        fileRepository.save(file);

        fileRepository.deleteById(file.getId());
        assertThat(fileRepository.findById(file.getId())).isEmpty();
    }

    @Test
    public void whenSavingNotUniquePathAndItIsEmpty() {
        Post post1 = new Post();
        postRepository.save(post1);
        Post post2 = new Post();
        postRepository.save(post2);

        File file1 = new File("test1", "/test");
        file1.setPostId(post1.getId());
        fileRepository.save(file1);
        File file2 = new File("test2", "/test");
        file2.setPostId(post2.getId());

        var result = fileRepository.save(file2);
        assertThat(result).isEmpty();
    }
}
