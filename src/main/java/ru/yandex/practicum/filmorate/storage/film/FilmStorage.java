package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
<<<<<<< HEAD
import ru.yandex.practicum.filmorate.model.Genre;
=======
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08

import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film);

    Film updateFilm(Film film);

<<<<<<< HEAD
    List<Film> getAllFilms();

    Film getFilmById(int id);

    List<Genre> getGenreByFilmId(int filmId);
=======
    void deleteAllFilms();

    List<Film> getAllFilms();

    List<Film> getPopularFilms(Integer quantity);

    Film getFilmById(int id);
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
}
