package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private static Map<Integer, User> users;
    private int id;

    public InMemoryUserStorage() {
        users = new HashMap<>();
        id = 0;
    }

    private void setId(User user) {
        if (user.getId() <= 0) {
            user.setId(++id);
        }
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User addUser(User user) {
        // Добавление пользователя в хранилище
        setId(user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        // Обновление пользователя в хранилище
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return user;
        } else {
            throw new NotFoundException("Пользователя с таким id " + user.getId() + " не существует");
        }
    }

    @Override
    public void deleteAllUsers() {
        // Удаление пользователя из хранилища
        users.clear();
        log.info("Пользователи удалены");
    }

    @Override
    public User getById(int id) {
        if (!users.containsKey(id)) {
            log.debug("Получение пользователя с неверным id: {}", id);
            throw new NotFoundException("Пользователя с id " + id + " не существует");
        }
        return users.get(id);
    }
}
