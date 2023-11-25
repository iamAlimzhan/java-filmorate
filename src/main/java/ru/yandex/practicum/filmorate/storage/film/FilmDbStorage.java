package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.mapper.MapperFilm;
import ru.yandex.practicum.filmorate.storage.mapper.MapperGenre;

import java.sql.Date;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.function.UnaryOperator.identity;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MapperFilm mapperFilm;

    @Override
    public Film addFilm(Film film) {
        log.info("Добавление фильма {}", film);
        String sql = "INSERT INTO film (name, description, release_date, duration, mpa_id) VALUES(?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getDuration());
            ps.setInt(5, film.getMpa().getId());
            return ps;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        addGenres(film);
        log.info("Фильм {} сохранен", film.getName());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        log.info("Обновление фильма {}", film);
        String sql = "UPDATE film SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ?" +
                " WHERE film_id = ?";
        int amountOperations = jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getId());
        if (amountOperations > 0) {
            deleteAllGenresFromFilm(film.getId());
            Set<Genre> genres = film.getGenres();
            addGenres(film);
            genres.clear();
            genres.addAll(getGenreByFilmId(film.getId()));
            return film;
        }
        log.debug("Фильм с id={} не найден", film.getId());
        throw new ValidationException(format("Не найден фильм с id %s", film.getId()));
    }

    @Override
    public List<Film> getAllFilms() {
        log.info("Получение списка всех фильмов");
        String sql = "SELECT f.*, m.mpa FROM film AS f JOIN mpa AS m ON f.mpa_id = m.mpa_id";
        List<Film> films = jdbcTemplate.query(sql, mapperFilm);
        final Map<Integer, Film> filmById = films.stream().collect(Collectors.toMap(Film::getId, identity()));
        String inSql = String.join(",", Collections.nCopies(films.size(), "?"));
        final String sqlQuery = "SELECT * from genre g, filmGenres fg where fg.genre_id = g.genre_id AND fg.film_id in " +
                "(" + inSql + ")";
        Map<Integer, Set<Genre>> genresByFilmId = new HashMap<>();
        jdbcTemplate.query(sqlQuery, (rs) -> {
            int filmId = rs.getInt("film_id");
            genresByFilmId.computeIfAbsent(filmId, k -> new HashSet<>()).add(makeGenre(rs));
        }, films.stream().map(Film::getId).toArray());
        genresByFilmId.forEach((filmId, genres) -> {
            Film film = filmById.get(filmId);
            if (film != null) {
                film.createGenre(genres);
            }
        });
        final String sqlLikes = "SELECT * FROM likes";
        jdbcTemplate.query(sqlLikes, (rs) -> {
            final Film film = filmById.get(rs.getInt("film_id"));
        });
        return films;
    }

    @Override
    public Film getFilmById(int id) {
        log.info("Получение фильма по id {}", id);
        String sql = "SELECT f.*, m.mpa FROM film AS f JOIN mpa AS m ON f.mpa_id = m.mpa_id WHERE f.film_id = ?";
        try {
            Film film = jdbcTemplate.queryForObject(sql, mapperFilm, id);
            List<Genre> genres = getGenreByFilmId(id);
            genres.forEach(genre -> film.getGenres().add(genre));
            return film;
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(format("Фильма с id= %s нет в базе", id));
        }
    }

    @Override
    public List<Genre> getGenreByFilmId(int filmId) {
        String sql = "SELECT g.* FROM filmGenres AS gf JOIN genre AS g ON gf.genre_id = g.genre_id WHERE " +
                "gf.film_id = ? ORDER BY g.genre_id";
        try {
            return jdbcTemplate.query(sql, new MapperGenre(), filmId);
        } catch (EmptyResultDataAccessException e) {
            log.debug("Фильма с id={} нет в базе", filmId);
            throw new ObjectNotFoundException(format("Фильма с id= %s нет в базе", filmId));
        }
    }

    private void addGenreToFilm(long filmId, int genreId) {
        String sql = "INSERT INTO filmGenres (film_id, genre_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, genreId);
    }

    private void deleteAllGenresFromFilm(long filmId) {
        String sql = "DELETE FROM filmGenres WHERE film_id = ?";
        jdbcTemplate.update(sql, filmId);
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        return Genre.builder()
                .id(rs.getInt("genre_id"))
                .name(rs.getString("genre"))
                .build();
    }

    private void addGenres(Film film) {
        String genreInsertSql = "INSERT INTO filmGenres (film_id, genre_id) VALUES (?, ?)";
        List<Object[]> batchArgs = new ArrayList<>();
        for (Genre genre : film.getGenres()) {
            batchArgs.add(new Object[]{film.getId(), genre.getId()});
        }
        jdbcTemplate.batchUpdate(genreInsertSql, batchArgs);
    }
}
