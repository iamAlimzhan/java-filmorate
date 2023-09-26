package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
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
        if (film.getDescription() == null || film.getDescription().length() > MAX_DESCRIPTION_LENGTH
                || film.getDescription().isEmpty()) {
            throw new ValidationException("В описании неверное количество символов");
        }
    }
}
