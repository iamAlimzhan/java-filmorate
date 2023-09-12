package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @GetMapping
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User getUserById(@PathVariable Integer id) {
        log.info("Получение пользователя по id: {}", id);
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    @ResponseBody
    public List<User> getFriends(@PathVariable Integer id) {
        log.info("Получение друзей пользователя id: {}", id);
        return userService.getListOfFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseBody
    public List<User> getMutualFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("Получение общих друзей id: {}, otherId: {}", id, otherId);
        return userService.getListOfMutualFriends(id, otherId);
    }

    @PostMapping
    @ResponseBody
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping
    @ResponseBody
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseBody
    public void addFriendById(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Добавление друга friendId:{} в дузья пользователя id: {}", friendId, id);
        userService.addToFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseBody
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Удаление друга friendId: {} из друзей пользователя id: {}", friendId, id);
        userService.deleteFriend(id, friendId);
    }
}