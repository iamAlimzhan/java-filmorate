package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film) throws ValidationException;

    Film updateFilm(Film film) throws ValidationException;

    void deleteAllFilms();

    List<Film> getAllFilms();

    List<Film> getPopularFilms(Integer quantity);

    Film getFilmById(int id);
}
