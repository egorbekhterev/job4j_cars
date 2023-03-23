package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.cars.service.FileService;

/**
 * REST-контроллер для обработки запросов, связанных с файлами.
 * @author: Egor Bekhterev
 * @date: 20.03.2023
 * @project: job4j_cars
 */
@RestController
@RequestMapping("/files")
@AllArgsConstructor
public class FileController {

    /**
     * Предоставляет функции для работы с файлами.
     */
    private final FileService fileService;

    /**
     * Обрабатывает GET-запросы, получая файлы по их идентификатору.
     * @param id идентификатор файла.
     * @return Если файл не найден, возвращает HTTP-ответ с кодом “404 Not Found”.
     * Если файл найден, возвращает HTTP-ответ с кодом “200 OK” и содержимым файла.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        var contentOptional = fileService.findById(id);
        if (contentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contentOptional.get().getContent());
    }
}
