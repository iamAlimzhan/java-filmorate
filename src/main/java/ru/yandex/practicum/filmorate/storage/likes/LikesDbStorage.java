package ru.yandex.practicum.filmorate.storage.likes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.mapper.MapperFilm;

import javax.validation.ValidationException;
import java.util.List;

import static java.lang.String.format;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikesDbStorage implements DaoLikes {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Integer> getFilmLikes(int filmId) {
        log.debug("Получение по id {}", filmId);
        try {
            return jdbcTemplate.query("SELECT user_id FROM likes WHERE film_id = ?",
                    (rs, rowNun) -> rs.getInt("user_id"), filmId);
        } catch (EmptyResultDataAccessException e) {
            log.debug("В бд не существует фильма с id {}", filmId);
            throw new NullPointerException(format("В бд не существует фильма с id %s", filmId));
        }
    }

    @Override
    public List<Film> getPopular(int count) {
        log.debug("Получение популярного фильма");
        return jdbcTemplate.query("SELECT f.*, m.mpa AS mpa_name FROM film AS f JOIN mpa AS m ON f.mpa_id = m.mpa_id" +
                        " LEFT JOIN (SELECT film_id, COUNT(user_id) AS likes_count FROM likes GROUP BY film_id ORDER BY " +
                        "likes_count) AS popular on f.film_id = popular.film_id ORDER BY popular.likes_count DESC LIMIT ?",
                new MapperFilm(), count);
    }

    @Override
    public void addLike(int filmId, int userId) {
        log.debug("Добавление лайка к фильму {} от пользователя {}", filmId, userId);
        try {
            jdbcTemplate.update("INSERT INTO likes (film_id, user_id) VALUES (?, ?)", filmId, userId);
        } catch (DataAccessException exception) {
            log.error("Поставлен лайк на фильм {}, от пользователя {}", userId, filmId);
            throw new ValidationException(format("Поставлен лайк на фильм %s, от пользователя %s",
                    userId, filmId));
        }
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        log.debug("Удаление лайка к фильму {} от пользователя {}", filmId, userId);
        jdbcTemplate.update("DELETE FROM likes WHERE (film_id = ? AND user_id = ?)", filmId, userId);
    }
}
