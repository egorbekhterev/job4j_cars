package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Сервисный класс для сохранения нового поста и связанных с ним файлов
 * @author: Egor Bekhterev
 * @date: 19.03.2023
 * @project: job4j_cars
 */
@Service
@AllArgsConstructor
@ThreadSafe
public class PostService {

    private PostRepository postRepository;

    private FileService fileService;

    /**
     * Сохраняет новые файлы, связанные с заданным постом. Связывает список файлов, созданных этим методом
     * с постом, с постом, который был сохранен в базе данных.
     * @param post   пост, с которым связаны новые файлы.
     * @param image  массив объектов FileDto, содержащий информацию о новых файлах.
     */
    private void saveNewFile(Post post, FileDto[] image) {
        List<File> files = Arrays.stream(image)
                .map(fileDto -> fileService.save(fileDto, post))
                .filter(Optional::isPresent)
                .map(Optional::get).toList();
        post.setImages(files);
    }

    /**
     * Сохраняет заданный пост и связанные с ним файлы. Логика сохранения следующая: сначала сохраняется сам пост,
     * а только потом файл, чтобы можно было передать id поста в таблицу files.
     * @param post   пост, который нужно сохранить.
     * @param image  массив объектов FileDto, содержащие информацию о файлах, связанных с этим постом.
     * @return       объект Optional<Post>, содержащий сохраненный пост, если операция прошла успешно,
     * или пустой Optional, если произошла ошибка
     */
    public Optional<Post> save(Post post, FileDto[] image) {
        var rsl = postRepository.save(post);
        saveNewFile(post, image);
        return rsl;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    public List<Post> findLastDay() {
        return postRepository.findLastDay();
    }

    public List<Post> findWithPhoto() {
        return postRepository.findWithPhoto();
    }

    public List<Post> findSameBrand(String brand) {
        return postRepository.findSameBrand(brand);
    }

    public boolean deleteById(int id) {
        return postRepository.deleteById(id);
    }

    public boolean updateStatusField(Post post) {
        return postRepository.updateStatusField(post);
    }
}
