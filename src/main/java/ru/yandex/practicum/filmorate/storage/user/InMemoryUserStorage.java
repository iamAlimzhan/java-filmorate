package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

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
            throw new RuntimeException("Пользователя с таким id " + user.getId() + " не существует");
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
            throw new UserNotFoundException("Пользователя с id " + id + " не существует");
        }
        return users.get(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryUserStorage that = (InMemoryUserStorage) o;
        return id == that.id && Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, id);
    }
}
