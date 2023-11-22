package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.likes.LikesDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikesDbStorage likesDbStorage;
    private static final LocalDate MIN_DATE_OF_RELEASE = LocalDate.of(1895, 12, 28);


    public List<Film> getAllFilms() {
        log.info("Лист фильмов {}", filmStorage.getAllFilms().size());
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(int filmId) {
        Film film;
        film = filmStorage.getFilmById(filmId);
        film.getGenres().addAll(filmStorage.getGenreByFilmId(filmId));
        film.getFilmLikes().addAll(likesDbStorage.getFilmLikes(filmId));
        log.info("Получен фильм по id {}", filmId);
        return film;
    }

    public Film addFilm(Film film) {
        filmValidate(film);
        Film createdFilm = filmStorage.addFilm(film);
        log.info("Добавлен фильм {}", createdFilm.getName());
        return createdFilm;
    }

    public Film updateFilm(Film film) {
        validateFilmById(film.getId());
        log.info("Обновлен фильм по id {}", film.getId());
        return filmStorage.updateFilm(film);
    }

    public void addLikes(int filmId, int userId) {
        validateFilmById(filmId);
        validateUserById(userId);
        log.info("К фильму {} пользоватлель {} поставил лайк", userId, filmId);
        likesDbStorage.addLike(filmId, userId);
    }

    public void deleteLikes(int filmId, int userId) {
        validateFilmById(filmId);
        validateUserById(userId);
        log.info("К фильму {} пользоватлель {} удалил лайк", userId, filmId);
        likesDbStorage.deleteLike(filmId, userId);
    }

    public List<Film> getPopularFilms(int count) {
        if (count <= 0) {
            log.error("количество фильмов отрицательное или равен 0");
            throw new ValidationException("количество фильмов отрицательное или равен 0 " + count);
        }
        return likesDbStorage.getPopular(count);
    }

    private void validateFilmById(int filmId) {
        filmStorage.getFilmById(filmId);
    }

    private void validateUserById(int userId) {
        userStorage.getById(userId);
=======
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;
    private static final LocalDate MIN_DATE_OF_RELEASE = LocalDate.of(1895, 12, 28);
    private static final int MAX_DESCRIPTION_LENGTH = 200;

    // получение фиьма по id
    public Film getFilmById(int id) {
        Film film = filmStorage.getFilmById(id);
        return film;
    }

    //получение фильмов
    public List<Film> getFilms() {
        return filmStorage.getAllFilms();
    }

    // добавление фильма
    public Film addFilm(Film film) {
        filmValidate(film);
        return filmStorage.addFilm(film);
    }

    //обновление фильма
    public Film updateFilm(Film film) {
        filmValidate(film);
        return filmStorage.updateFilm(film);
    }

    // добавление Лайка к фильму
    public void addLikeForFilm(Integer id, Integer userId) {
        Film film = filmStorage.getFilmById(id);
        userStorage.getById(userId);
        film.putLike(userId);
    }

    // удаление Лайка к фильму
    public void deleteLikeForFilm(Integer id, Integer userId) {
        Film film = filmStorage.getFilmById(id);
        userStorage.getById(userId);
        film.deleteLike(userId);
    }

    // вывод списка популярных фильмов
    public List<Film> getPopularFilms(Integer count) {
        return filmStorage.getPopularFilms(count);
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
    }

    private void filmValidate(Film film) {
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(MIN_DATE_OF_RELEASE)) {
            throw new ValidationException("Неверная дата релиза");
        }
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("Название фильма не должно быть пустым");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма не может быть меньше 0");
        }
<<<<<<< HEAD
        if (film.getDescription() == null || film.getDescription().length() > 200
=======
        if (film.getDescription() == null || film.getDescription().length() > MAX_DESCRIPTION_LENGTH
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
                || film.getDescription().isEmpty()) {
            throw new ValidationException("В описании неверное количество символов");
        }
    }
}
