package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.CarDto;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Класс-контроллер для обработки запросов, связанных с постами {@link Post}.
 * @author: Egor Bekhterev
 * @date: 20.03.2023
 * @project: job4j_cars
 */
@ThreadSafe
@Controller
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    private final BrandService brandService;

    private final BodyTypeService bodyTypeService;

    private final EngineService engineService;

    private final CarService carService;

    private final FileService fileService;

    /**
     * Обрабатывает GET-запросы на главную страницу ("/" или “/index”). Добавляет в модель содержимое постов и первое
     * фото соответсвующее этим постам.
     * @param model модель, используемая для передачи данных на страницу.
     * @return путь к представлению.
     */
    @GetMapping({"/", "/index"})
    public String getPosts(Model model) {
        List<File> files = postService.findAll().stream()
                .map(p -> fileService.findByPostId(p.getId()).get(0))
                .toList();
        model.addAttribute("files", files);
        model.addAttribute("posts", postService.findAll());
        return "posts/list";
    }

    /**
     * Обработчик GET-запроса для страницы создания поста. Аттрибуты для модели используются для
     * создания объекта {@link Car}.
     * @param model модель, используемая для передачи данных на страницу.
     * @return путь к представлению.
     */
    @GetMapping("posts/create")
    public String getCreationPage(Model model) {
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("bodies", bodyTypeService.findAll());
        model.addAttribute("engines", engineService.findAll());
        return "posts/create";
    }

    /**
     * POST-метод для создания {@link Post}. Собранный из User и Car пост сохраняется вместе полученными файлами
     * с помощью метода fileService.save(Post post, FileDto[] image).
     * @param post Объект {@link Post}.
     * @param model Модель, используемая для передачи данных на страницу.
     * @param user Объект пользователя, создающего {@link Post}. Будет добавлен к посту как свойство.
     * @param carDto Объект CarDto, содержащий информацию о машине, для которой создается пост.
     * @param file массив фотографий, который прикрепляются к сообщению.
     * @return путь к представлению.
     */
    @PostMapping("posts/create")
    public String create(@ModelAttribute Post post, Model model, @SessionAttribute User user,
                         @ModelAttribute CarDto carDto, @RequestParam MultipartFile[] file) {
        Car car = new Car();
        car.setName(carDto.getModel());
        car.setBrand(brandService.findById(carDto.getBrandId()).get());
        car.setBodyType(bodyTypeService.findById(carDto.getBodyId()).get());
        car.setEngine(engineService.findById(carDto.getEngineId()).get());
        carService.save(car);

        post.setCar(car);
        post.setUser(user);

        postService.save(post, Arrays.stream(file).map(f -> {
            try {
                return new FileDto(f.getOriginalFilename(),
                        f.getBytes());
            } catch (IOException e) {
                model.addAttribute("message", e.getMessage());
                return "errors/404";
            }
        }).toArray(FileDto[]::new));
        return "redirect:/index";
    }

    /**
     * GET-метод для получения {@link Post} по идентификатору.
     * @param model Модель, используемая для передачи данных на страницу.
     * @param id Идентификатор поста, переданный в URL-адресе.
     * @return Путь к представлению. Если пост не найден, возвращает страницу с ошибкой 404.
     */
    @GetMapping("posts/{id}")
    public String getById(Model model, @PathVariable int id) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "No post with the given ID is found.");
            return "errors/404";
        }
        var fileList = fileService.findByPostId(postOptional.get().getId());
        model.addAttribute("images", fileList);
        model.addAttribute("post", postOptional.get());
        return "posts/one";
    }

    /**
     * GET-метод для изменения статуса объявления. Сравнивает пользователя, создавшего пост и пользователя текущей
     * сессии для согласования изменения.
     * @param model Модель, используемая для передачи данных на страницу.
     * @param id Идентификатор поста, переданный в URL-адресе.
     * @param user Пользователь текущей сессии.
     * @return Путь к представлению. Если пост не найден или статус поста не может быть изменен текущим пользователем,
     * возвращает страницу с ошибкой 404.
     */
    @GetMapping("posts/sell/{id}")
    public String sell(Model model, @PathVariable int id, @SessionAttribute User user) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "No post with the given ID is found.");
            return "errors/404";
        }
        if (!postOptional.get().getUser().equals(user)) {
            model.addAttribute("message",
                    "The status of a post can only be changed by the user who created it.");
            return "errors/404";
        }
        postService.updateStatusField(postOptional.get());
        return "redirect:/index";
    }
}
