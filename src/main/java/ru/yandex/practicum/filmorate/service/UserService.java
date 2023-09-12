package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    //получение пользователя по id
    public User getUserById(int id) {
        return userStorage.getById(id);
    }

    // получение пользователей
    public List<User> getUsers() {
        return userStorage.getAll();
    }

    //добавление пользователя
    public User addUser(User user) throws ValidationException {
        userValidate(user);
        return userStorage.addUser(user);
    }

    //обновление пользователя
    public User updateUser(User user) throws ValidationException {
        userValidate(user);
        return userStorage.updateUser(user);
    }

    //добавление друга
    public void addToFriend(Integer id, Integer friendId) {
        User user = userStorage.getById(id);
        User friend = userStorage.getById(friendId);
        if (friendId == null || id == null) {
            throw new UserNotFoundException("Этого пользователя не существует");
        }
        user.getFriends().add(friendId);
        friend.getFriends().add(id);
    }

    // удаление друга
    public void deleteFriend(Integer id, Integer friendId) {
        User user = userStorage.getById(id);
        User friend = userStorage.getById(friendId);
        if (user == null || friend == null) {
            throw new UserNotFoundException("Этого пользователя не существует");
        }
        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);
    }

    // вывод списка общих друзей
    public List<User> getListOfMutualFriends(Integer id, Integer mutualId) {
        User user = userStorage.getById(id);
        User mutualFriends = userStorage.getById(mutualId);

        if (user == null || mutualFriends == null) {
            throw new UserNotFoundException("Этого пользователя не существует");
        }

        Set<Integer> userFriends = user.getFriends();
        Set<Integer> mutualFriendsList = mutualFriends.getFriends();

        List<User> mutualFriendsResult = new ArrayList<>();

        for (Integer userId : userFriends) {
            if (mutualFriendsList.contains(userId)) {
                User mutualFriend = userStorage.getById(userId);
                if (mutualFriend != null) {
                    mutualFriendsResult.add(mutualFriend);
                }
            }
        }

        return mutualFriendsResult;
    }


    public List<User> getListOfFriends(int id) {
        User user = userStorage.getById(id);
        if (user == null) {
            throw new UserNotFoundException("Этого пользователя не существует");
        }

        Set<Integer> friendsList = user.getFriends();
        if (friendsList.isEmpty()) {
            throw new UserNotFoundException("Список пустой");
        }

        List<User> friendObjects = new ArrayList<>();
        for (Integer friendId : friendsList) {
            User friend = userStorage.getById(friendId);
            if (friend != null) {
                friendObjects.add(friend);
            }
        }

        log.info("Список друзей выведен");
        return friendObjects;
    }

    private void userValidate(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new javax.validation.ValidationException("Неверный email");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new javax.validation.ValidationException("Неверный login");
        }
        if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) {
            throw new javax.validation.ValidationException("Неверная дата");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }
    }
}
