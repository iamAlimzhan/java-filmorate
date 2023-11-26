package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mapper.MapperMpa;

import java.util.List;

import static java.lang.String.format;

@Slf4j
@Component
@RequiredArgsConstructor
public class MpaDbStorage implements DaoMpa {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getMpaList() {
        log.debug("Получение списка рейтингов");
        return jdbcTemplate.query("SELECT * FROM mpa ORDER BY mpa_id", new MapperMpa());
    }

    @Override
    public Mpa getById(int id) {
        log.info("Получение рейтинга по id {}", id);
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM mpa WHERE mpa_id = ?", new MapperMpa(), id);
        } catch (EmptyResultDataAccessException exp) {
            log.debug("Не найден рейтинг с id {}", id);
            throw new ObjectNotFoundException(format("Не найден рейтинг с id %s", id));
        }
    }

}
