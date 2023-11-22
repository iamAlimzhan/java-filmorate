package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.DaoGenre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final DaoGenre daoGenre;

    public List<Genre> getGenres() {
        return daoGenre.getGenreList();
    }

    public Genre getById(int id) {
        return daoGenre.getById(id);
    }
}
