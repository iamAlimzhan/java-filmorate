package ru.yandex.practicum.filmorate.storage.friends;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface DaoFriends {
    List<User> getFriendsList(int userId);

    List<User> getMutualFriends(int userId, int friendId);

    void add(int userId, int friendId);

    void delete(int userId, int friendId);

    void update(int userId, int friendId, boolean isConfirm);
}
