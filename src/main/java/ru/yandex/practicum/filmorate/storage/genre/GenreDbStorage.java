package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.mapper.MapperGenre;

import java.util.List;

import static java.lang.String.format;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenreDbStorage implements DaoGenre {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getGenreList() {
        log.debug("Получение списка жанров");
        return jdbcTemplate.query("SELECT * FROM genre ORDER BY genre_id", new MapperGenre());
    }

    @Override
    public Genre getById(int id) {
        log.debug("Получение по id {}", id);
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM genre WHERE genre_id=?", new MapperGenre(), id);
        } catch (EmptyResultDataAccessException exp) {
            log.debug("Жанр по id {} не найден", id);
            throw new ObjectNotFoundException(format("Жанр по id %s не найден", id));
        }
    }
}
