package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friends.FriendsDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final FriendsDbStorage friendsDbStorage;

    //получение пользователя по id
    public User getUserById(Integer id) {
        User user = userStorage.getById(id);
        Set<Integer> usersFriends = user.getFriends();
        usersFriends.addAll(friendsDbStorage.getFriendsList(user.getId()).stream().map(User::getId)
                .collect(Collectors.toSet()));
=======
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    //получение пользователя по id
    public User getUserById(int id) {
        User user = userStorage.getById(id);
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
        return user;
    }

    // получение пользователей
    public List<User> getUsers() {
<<<<<<< HEAD
        List<User> allUsers = userStorage.getAll();
        for (User user : allUsers) {
            Set<Integer> usersFriends = user.getFriends();
            usersFriends.addAll(friendsDbStorage.getFriendsList(user.getId()).stream().map(User::getId)
                    .collect(Collectors.toSet()));
        }
        return allUsers;
=======
        return userStorage.getAll();
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
    }

    //добавление пользователя
    public User addUser(User user) {
        userValidate(user);
<<<<<<< HEAD
        Set<Integer> usersFriends = user.getFriends();
        for (Integer friendId : usersFriends) {
            if (!userStorage.getAll().contains(userStorage.getById(friendId))) {
                usersFriends.remove(friendId);
                throw new ValidationException(format("Не существует пользователя %s", friendId));
            }
        }
        User createdUser = userStorage.addUser(user);
        Set<Integer> friends = user.getFriends();
        for (Integer friendId : friends) {
            addToFriend(createdUser.getId(), friendId);
        }
        return createdUser;
=======
        return userStorage.addUser(user);
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
    }

    //обновление пользователя
    public User updateUser(User user) {
<<<<<<< HEAD
        userStorage.getById(user.getId());
        Set<Integer> userFriends = user.getFriends();
        userFriends.forEach(friendsId -> addToFriend(user.getId(), friendsId));
=======
        userValidate(user);
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
        return userStorage.updateUser(user);
    }

    //добавление друга
    public void addToFriend(Integer id, Integer friendId) {
<<<<<<< HEAD
        if (id == friendId) {
            throw new ValidationException("Невозможно добавить себя в друзья");
        }
        User user = getUserById(id);
        User friend = getUserById(friendId);
        Set<Integer> usersFriends = user.getFriends();
        Set<Integer> friendsFriends = friend.getFriends();
        boolean isUserHasFriend = usersFriends.contains(friendId);
        boolean isFriendHasUser = friendsFriends.contains(id);
        if (!isUserHasFriend && !isFriendHasUser) {
            friendsDbStorage.add(id, friendId);
            usersFriends.add(friendId);
        } else if (!isUserHasFriend) {
            friendsDbStorage.add(id, friendId);
            friendsDbStorage.update(id, friendId, true);
            friendsDbStorage.update(friendId, id, true);
            usersFriends.add(friendId);
        } else {
            throw new ValidationException(format("Пользователь %s в друзьях у другого пользователя %s",
                    friendId, id));
        }
=======
        User user = userStorage.getById(id);
        User friend = userStorage.getById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(id);
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
    }

    // удаление друга
    public void deleteFriend(Integer id, Integer friendId) {
<<<<<<< HEAD
        User user = getUserById(id);
        User friend = getUserById(friendId);
        Set<Integer> usersFriends = user.getFriends();
        Set<Integer> friendsFriends = friend.getFriends();
        if (!usersFriends.contains(friendId)) {
            throw new ValidationException(format("Пользователь %s не в друзьях у другого пользователя %s",
                    friendId, id));
        } else if (!friendsFriends.contains(id)) {
            friendsDbStorage.delete(id, friendId);
        } else {
            friendsDbStorage.delete(id, friendId);
            friendsDbStorage.update(friendId, id, false);
        }
=======
        User user = userStorage.getById(id);
        User friend = userStorage.getById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
    }

    // вывод списка общих друзей
    public List<User> getListOfMutualFriends(Integer id, Integer mutualId) {
<<<<<<< HEAD
        return friendsDbStorage.getMutualFriends(id, mutualId);
    }

    public List<User> getListOfFriends(Integer id) {
        return friendsDbStorage.getFriendsList(id);
=======
        User user = userStorage.getById(id);
        User mutualFriends = userStorage.getById(mutualId);

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


        Set<Integer> friendsList = user.getFriends();
        if (friendsList.isEmpty()) {
            throw new NotFoundException("Список пустой");
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
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
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
<<<<<<< HEAD

=======
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
