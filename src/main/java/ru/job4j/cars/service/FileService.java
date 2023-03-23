package ru.job4j.cars.service;

import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author: Egor Bekhterev
 * @date: 19.03.2023
 * @project: job4j_cars
 */
@Service
@ThreadSafe
public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRepository.class.getName());

    private final FileRepository fileRepository;

    private final String storageDirectory;

    /**
     * Создает директорию по указанному пути. Если директория уже существует, метод не делает ничего.
     * @param path путь, где должна быть создана директория.
     */
    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileService(FileRepository fileRepository, @Value("${file.directory}") String storageDirectory) {
        this.fileRepository = fileRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    /**
     * Генерирует уникальное имя для файла, основанное на переданном имени файла.
     * @param sourceName исходное имя файла, которое будет использоваться при формировании уникального имени.
     * @return уникальное имя файла, состоящее из разделителя, UUID и исходным именем в конце.
     */
    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }

    /**
     * Записывает массив байтов в файл на указанном пути. Если файл уже существует, его содержимое будет заменено новым.
     * @param path путь к файлу, в который нужно записать содержимое.
     * @param content массив байтов, который нужно записать в файл.
     */
    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<File> save(FileDto fileDto, Post post) {
        var path = getNewFilePath(fileDto.getName());
        writeFileBytes(path, fileDto.getContent());
        File file = new File(fileDto.getName(), path);
        file.setPostId(post.getId());
        return fileRepository.save(file);
    }

    /**
     * Метод для чтения содержимого файла в виде массива байт.
     * @param path путь к файлу
     * @return массив байт содержимого файла
     */
    private byte[] readFileBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<FileDto> findById(int id) {
        var fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            LOGGER.error("No file with the given ID is found.");
        }
        var content = readFileBytes(fileOptional.get().getPath());
        return Optional.of(new FileDto(fileOptional.get().getName(), content));
    }

    public List<File> findByPostId(int postId) {
        return fileRepository.findByPostId(postId);
    }

    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(int id) {
        var fileOptional = fileRepository.findById(id);
        deleteFile(fileOptional.get().getPath());
        fileRepository.deleteById(id);
    }
}
