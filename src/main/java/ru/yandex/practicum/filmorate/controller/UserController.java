package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@Slf4j
public class UserController {
    private List<User> users = new ArrayList<>();
    private int nextUserId = 1;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Создан новый пользователь: {}", user.getName());
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Неправильный формат электронной почты");
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        user.setId(nextUserId++);
        users.add(user);
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @Valid @RequestBody User updatedUser) {
        log.info("Обновлен новый пользователь: {}", updatedUser.getName());
        if (!updatedUser.getEmail().contains("@")) {
            throw new ValidationException("Неправильный формат электронной почты");
        }
        if (updatedUser.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы");
        }
        if (updatedUser.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        for (User user : users) {
            if (user.getId() == id) {
                user.setEmail(updatedUser.getEmail());
                user.setLogin(updatedUser.getLogin());
                user.setName(updatedUser.getName());
                user.setBirthday(updatedUser.getBirthday());
                return user;
            }
        }
        return null; // User не найден
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Получен новый пользователь: {}", users);
        return users;
    }
}
