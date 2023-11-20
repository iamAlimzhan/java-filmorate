package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    void deleteAllFilms();

    List<Film> getAllFilms();

    List<Film> getPopularFilms(Integer quantity);

    Film getFilmById(int id);
}
