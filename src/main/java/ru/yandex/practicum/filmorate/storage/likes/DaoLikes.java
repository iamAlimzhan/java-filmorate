package ru.yandex.practicum.filmorate.storage.likes;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface DaoLikes {
    List<Integer> getFilmLikes(int filmId);

    List<Film> getPopular(int count);

    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);
}
