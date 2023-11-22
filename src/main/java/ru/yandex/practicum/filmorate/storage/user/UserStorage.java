package ru.yandex.practicum.filmorate.storage.user;


import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User addUser(User user);

    User updateUser(User user);

<<<<<<< HEAD
=======
    void deleteAllUsers();

>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
    User getById(int id);

    List<User> getAll();
}
