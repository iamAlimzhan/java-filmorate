package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface DaoGenre {
    List<Genre> getGenreList();

    Genre getById(int id);
}
