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

    private void saveNewFile(Post post, FileDto[] image) {
        List<File> files = Arrays.stream(image)
                .map(fileService::save)
                .filter(Optional::isPresent)
                .map(Optional::get).toList();
        post.setImages(files);
    }

    public Optional<Post> save(Post post, FileDto[] image) {
        saveNewFile(post, image);
        return postRepository.save(post);
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
}
