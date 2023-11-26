package ru.yandex.practicum.filmorate.storage.friends;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.mapper.MapperUser;

import javax.validation.ValidationException;
import java.util.List;

import static java.lang.String.format;

@Slf4j
@Component
@RequiredArgsConstructor
public class FriendsDbStorage implements DaoFriends {
    private final JdbcTemplate jdbcTemplate;
    private final MapperUser mapperUser;

    @Override
    public List<User> getFriendsList(int userId) {
        log.debug("Список пользователей id {}", userId);
        try {
            log.info("Список друзей пользователя {}", userId);
            return jdbcTemplate.query("SELECT * FROM users WHERE user_id IN (SELECT friend_id FROM friends " +
                    "WHERE user_id = ?)", mapperUser, userId);
        } catch (EmptyResultDataAccessException e) {
            throw new NullPointerException(format("Пользователя с id= %s нет в базе", userId));
        }
    }

    @Override
    public List<User> getMutualFriends(int userId, int friendId) {
        log.debug("Получение общих друзей по id {}, {}", userId, friendId);
        return jdbcTemplate.query("SELECT u.user_id, u.email, u.name, u.login, u.birthday " +
                "FROM users u " +
                "JOIN friends f1 ON u.user_id = f1.friend_id " +
                "JOIN friends f2 ON f1.friend_id = f2.friend_id " +
                "WHERE f1.user_id = ? " +
                "AND f2.user_id = ?", mapperUser, userId, friendId);
    }

    @Override
    public void add(int userId, int friendId) {
        try {
            jdbcTemplate.update("INSERT INTO friends (user_id, friend_id, is_confirm) VALUES(?, ?, false)", userId, friendId);
        } catch (DataAccessException exception) {
            throw new ValidationException(String.format("У пользователя %s уже есть друг %s", userId, friendId));
        }
    }

    @Override
    public void delete(int userId, int friendId) {
        log.debug("Удаление друзей {}, {}", userId, friendId);
        jdbcTemplate.update("DELETE FROM friends WHERE user_id = ? AND friend_id = ?", userId, friendId);
    }

    public void update(int userId, int friendId, boolean isConfirm) {
        log.debug("Обновление друзей {}, {}, {}", userId, friendId, isConfirm);
        jdbcTemplate.update("UPDATE Friendship SET status = ? WHERE user_id = ? AND friend_id = ?", userId, friendId,
                isConfirm);
    }
}
