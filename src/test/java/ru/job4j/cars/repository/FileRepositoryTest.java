package ru.job4j.cars.repository;

import config.HibernateTestConfiguration;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.File;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class FileRepositoryTest {

    private final FileRepository fileRepository = new FileRepository(
            new CrudRepository(HibernateTestConfiguration.getSessionFactory()));

    @Test
    public void whenSaveFileThenGetItById() {
        File file = new File("test", "/test");
        fileRepository.save(file);
        var result = fileRepository.findById(file.getId());
        assertThat(result.get()).isEqualTo(file);
    }

    @Test
    public void whenSaveFilesThenAllFound() {
        File file1 = new File("test1", "/test1");
        File file2 = new File("test2", "/test2");
        fileRepository.save(file1);
        fileRepository.save(file2);

        var result = fileRepository.findAll();
        assertThat(result).isEqualTo(List.of(file1, file2));
    }

    @Test
    public void whenDeleteFileThenItIsEmpty() {
        File file = new File("test", "/test");
        fileRepository.save(file);

        fileRepository.deleteById(file.getId());
        assertThat(fileRepository.findById(file.getId())).isEmpty();
    }

    @Test
    public void whenNotUniquePathAndSave() {
        File file1 = new File("test1", "/test");
        fileRepository.save(file1);
        File file2 = new File("test2", "/test");

        var result = fileRepository.save(file2);
        assertThat(result).isEmpty();
    }
}
