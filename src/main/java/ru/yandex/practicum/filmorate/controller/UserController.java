package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private Map<Integer, User> users = new ConcurrentHashMap<>();
    private int id = 0;

    @GetMapping
    @ResponseBody
    public List<User> getAllUsers() {
        log.info("Количсетво пользователей: '{}'", users.size());
        return new ArrayList<>(users.values());
    }

    @PostMapping
    @ResponseBody
    public User addUser(@RequestBody User user) {
        userValidate(user);
        users.put(user.getId(), user);
        log.info("id сохраненного пользователя '{}'", user.getId());
        return user;
    }

    @PutMapping
    @ResponseBody
    public User updateUser(@RequestBody User user) {
        userValidate(user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("id по которому находится пользователь '{}' обновлён", user.getId());
        } else {
            throw new ValidationException("С id " + user.getId() + " не существует пользователя");
        }
        return user;
    }


    private void userValidate(User user) {
        if (user == null) {
            throw new ValidationException("Пользователь не может быть null");
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Неверный email");
        }
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            throw new ValidationException("Неверный login");
        }
        if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Неверная дата");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getId() <= 0) {
            user.setId(++id);
            log.info("Неверный идентификатор пользователя был задан как '{}'", user.getId());
        }
    }
}