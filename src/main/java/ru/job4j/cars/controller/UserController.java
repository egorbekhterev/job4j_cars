package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.service.UserService;
import ru.job4j.cars.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author: Egor Bekhterev
 * @date: 20.03.2023
 * @project: job4j_cars
 */
@ThreadSafe
@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * GET-метод для отображения страницы регистрации пользователя {@link User}.
     * @return путь к представлению.
     */
    @GetMapping("/register")
    public String getRegistrationPage() {
        return "users/register";
    }

    /**
     * POST-метод для регистрации пользователя {@link User}. Уникальность пользователя реализована на уровне БД.
     * @param model - модель для сборки представления.
     * @param user - объект User, который будет сохранен в таблицу users.
     * @return путь к представлению.
     */
    @PostMapping("/register")
    public String register(Model model, @ModelAttribute User user) {
        var savedUser = userService.create(user);
        if (savedUser.isEmpty()) {
            model.addAttribute("message", "A user with this login already exists.");
            return "errors/404";
        }
        return "redirect:/index";
    }

    /**
     * GET-метод для отображения страницы входа в систему {@link User}.
     * @return путь к представлению.
     */
    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    /**
     * POST-метод для входа в систему {@link User}. Проверяет, есть ли пользователь в системе. Реализовано сохранение
     * пользовательской сессии.
     * P.S. под капотом HttpSession ConcurrentHashMap - либо запись, либо чтение.
     * @param user - объект User.
     * @param model - модель для сборки представления.
     * @param request - объект для получения {@link HttpSession}.
     * @return путь к представлению.
     */
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
        var userOptional = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Login or password entered incorrectly.");
            return "users/login";
        }
        var session = request.getSession();
        session.setAttribute("user", userOptional.get());
        return "redirect:/index";
    }

    /**
     * GET-метод для выхода из системы. Удаляет данные, связанные с пользователем.
     * @param session - текущая пользовательская сессия.
     * @return перенаправляет на страницу входа в систему.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }

    /**
     * GET-метод для отображения страницы пользователя {@link User}.
     * @return путь к представлению.
     */
    @GetMapping("/{id}")
    public String getUserProfile(Model model, @SessionAttribute(required = false) User user) {
        return "users/one";
    }
}
