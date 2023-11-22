package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.mapper.MapperUser;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User addUser(User user) {
        log.info("Создание юзера");
        String sql = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setDate(4, Date.valueOf(user.getBirthday()));
            return ps;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        log.info("Обновление юзера");
        try {
            jdbcTemplate.update("UPDATE users SET email=?, login=?, name=?, birthday=? WHERE user_id=?",
                    user.getEmail(), user.getLogin(), user.getName(), Date.valueOf(user.getBirthday()), user.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(format("Нет пользователя в бд с id = %s", user.getId()));
        }
        return user;
    }

    @Override
    public User getById(int id) {
        log.info("Получение юзера по id: {}", id);
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id = ?", new MapperUser(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(format("Нет пользователя в бд с id = %s", id));
        }
    }

    @Override
    public List<User> getAll() {
        log.info("Получение списка всех юзеров");
        return jdbcTemplate.query("SELECT * FROM users", new MapperUser());
    }
}
