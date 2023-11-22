package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
<<<<<<< HEAD
=======

    public UserController(UserService userService) {
        this.userService = userService;
    }
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
<<<<<<< HEAD
    @ResponseBody
    public User getUserById(@PathVariable Integer id) {
=======
    public User getUserById(@PathVariable Integer id) {
        log.info("Получение пользователя по id: {}", id);
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/friends")
<<<<<<< HEAD
    @ResponseBody
    public List<User> getFriends(@PathVariable Integer id) {
=======
    public List<User> getFriends(@PathVariable Integer id) {
        log.info("Получение друзей пользователя id: {}", id);
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
        return userService.getListOfFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
<<<<<<< HEAD
    @ResponseBody
    public List<User> getMutualFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
=======
    public List<User> getMutualFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("Получение общих друзей id: {}, otherId: {}", id, otherId);
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
        return userService.getListOfMutualFriends(id, otherId);
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
<<<<<<< HEAD
    @ResponseBody
    public void addFriendById(@PathVariable Integer id, @PathVariable Integer friendId) {
=======
    public void addFriendById(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Добавление друга friendId:{} в дузья пользователя id: {}", friendId, id);
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
        userService.addToFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
<<<<<<< HEAD
    @ResponseBody
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
=======
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Удаление друга friendId: {} из друзей пользователя id: {}", friendId, id);
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
        userService.deleteFriend(id, friendId);
    }
}