package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friends.FriendsDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final FriendsDbStorage friendsDbStorage;

    //получение пользователя по id
    public User getUserById(Integer id) {
        User user = userStorage.getById(id);
        return user;
    }

    // получение пользователей
    public List<User> getUsers() {
        return userStorage.getAll();
    }

    //добавление пользователя
    public User addUser(User user) {
        userValidate(user);
        User createdUser = userStorage.addUser(user);
        return createdUser;
    }

    //обновление пользователя
    public User updateUser(User user) {
        userStorage.getById(user.getId());
        return userStorage.updateUser(user);
    }

    //добавление друга
    public void addToFriend(Integer id, Integer friendId) {
        if (id == friendId) {
            throw new ValidationException("Невозможно добавить себя в друзья");
        }
        User user = getUserById(id);
        User friend = getUserById(friendId);
        friendsDbStorage.add(id, friendId);
    }

    // удаление друга
    public void deleteFriend(Integer id, Integer friendId) {
        User user = getUserById(id);
        User friend = getUserById(friendId);
        friendsDbStorage.delete(id, friendId);
    }

    // вывод списка общих друзей
    public List<User> getListOfMutualFriends(Integer id, Integer mutualId) {
        return friendsDbStorage.getMutualFriends(id, mutualId);
    }

    public List<User> getListOfFriends(Integer id) {
        return friendsDbStorage.getFriendsList(id);
    }

    private void userValidate(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Неверный email");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Неверный login");
        }
        if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Неверная дата");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}

