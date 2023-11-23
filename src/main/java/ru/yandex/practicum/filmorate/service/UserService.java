package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
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
        return user;
    }

    // получение пользователей
    public List<User> getUsers() {
        List<User> allUsers = userStorage.getAll();
        for (User user : allUsers) {
            Set<Integer> usersFriends = user.getFriends();
            usersFriends.addAll(friendsDbStorage.getFriendsList(user.getId()).stream().map(User::getId)
                    .collect(Collectors.toSet()));
        }
        return allUsers;
    }

    //добавление пользователя
    public User addUser(User user) {
        userValidate(user);
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
    }

    //обновление пользователя
    public User updateUser(User user) {
        userStorage.getById(user.getId());
        Set<Integer> userFriends = user.getFriends();
        userFriends.forEach(friendsId -> addToFriend(user.getId(), friendsId));
        return userStorage.updateUser(user);
    }

    //добавление друга
    public void addToFriend(Integer id, Integer friendId) {
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
    }

    // удаление друга
    public void deleteFriend(Integer id, Integer friendId) {
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

