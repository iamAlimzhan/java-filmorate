package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private static Map<Integer, Film> films;
    private int id;

    public InMemoryFilmStorage() {
        films = new ConcurrentHashMap<>();
        id = 0;
    }

    private void setId(Film film) {
        if (film.getId() <= 0) {
            film.setId(++id);
        }
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public List<Film> getPopularFilms(Integer count) {
        log.info("Самые популярные фильмы");
        return getAllFilms()
                .stream()
                .sorted(new Comparator<Film>() {
                    @Override
                    public int compare(Film film1, Film film2) {
                        return Integer.compare(film2.getFilmLikes().size(), film1.getFilmLikes().size());
                    }
                })
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public Film addFilm(Film film) {
        // Добавление фильма в хранилище
        setId(film);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getFilmById(int id) {
        if (!films.containsKey(id)) {
            log.debug("Получение фильма с неверным id: {}", id);
            throw new FilmNotFoundException("Фильма с id " + id + " не существует");
        }
        return films.get(id);
    }

    @Override
    public Film updateFilm(Film film) {
        // Обновление фильма в хранилище
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        } else {
            throw new RuntimeException("Фильма с таким id не существует");
        }
    }

    @Override
    public void deleteAllFilms() {
        // Удаление фильма из хранилища
        films.clear();
        log.info("Фильмы удалены");
    }
}
