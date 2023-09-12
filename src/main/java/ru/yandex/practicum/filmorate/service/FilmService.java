package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final UserService userService;
    private final FilmStorage filmStorage;
    public static final LocalDate MIN_DATE_OF_RELEASE = LocalDate.of(1895, 12, 28);
    public static final int MAX_DESCRIPTION_LENGTH = 200;

    // получение фиьма по id
    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    //получение фильмов
    public List<Film> getFilms() {
        return filmStorage.getAllFilms();
    }

    // добавление фильма
    public Film addFilm(Film film) throws ValidationException {
        filmValidate(film);
        return filmStorage.addFilm(film);
    }

    //обновление фильма
    public Film updateFilm(Film film) throws ValidationException {
        filmValidate(film);
        return filmStorage.updateFilm(film);
    }

    // добавление Лайка к фильму
    public void addLikeForFilm(Integer id, Integer userId) {
        Film film = filmStorage.getFilmById(id);
        userService.getUserById(userId);
        film.putLike(userId);
    }

    // удаление Лайка к фильму
    public void deleteLikeForFilm(Integer id, Integer userId) {
        Film film = filmStorage.getFilmById(id);
        userService.getUserById(userId);
        film.deleteLike(userId);
    }

    // вывод списка популярных фильмов
    public List<Film> getPopularFilms(Integer count) {
        return filmStorage.getPopularFilms(count);
    }

    private void filmValidate(Film film) {
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(MIN_DATE_OF_RELEASE)) {
            throw new javax.validation.ValidationException("Неверная дата релиза");
        }
        if (film.getName() == null || film.getName().isBlank()) {
            throw new javax.validation.ValidationException("Название фильма не должно быть пустым");
        }
        if (film.getDuration() <= 0) {
            throw new javax.validation.ValidationException("Продолжительность фильма не может быть меньше 0");
        }
        if (film.getDescription() == null || film.getDescription().length() > MAX_DESCRIPTION_LENGTH
                || film.getDescription().isEmpty()) {
            throw new javax.validation.ValidationException("В описании неверное количество символов");
        }
        if (film.getFilmLikes() == null) {
            film.setFilmLikes(new HashSet<>());
        }
    }
}