package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private Map<Integer, Film> films = new ConcurrentHashMap<>();
    public static final LocalDate MIN_DATE_OF_RELEASE = LocalDate.of(1985, 12, 28);
    public static final int MAX_DESCRIPTION_LENGTH = 200;
    private int id = 0;

    @GetMapping
    @ResponseBody
    public List<Film> getAllFilms() {
        log.info("Количество фильмов: '{}'", films.size());
        return new ArrayList<>(films.values());
    }


    @PostMapping
    @ResponseBody
    public Film addFilm(@RequestBody Film film) throws ValidationException {
        filmValidate(film);
        films.put(film.getId(), film);
        log.info("Фильм '{}' сохранен с id '{}'", film.getName(), film.getId());
        return film;
    }

    @PutMapping
    @ResponseBody
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        filmValidate(film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("id под которым сохранен фильм '{}' обновлён", film.getId());
        } else {
            throw new ValidationException("Фильма с таким id " + film.getId() + " нет");
        }
        return film;
    }

    private void filmValidate(Film film) throws ValidationException {
        if (film.getReleaseDate() == null ||
                film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Неверная дата релиза");
        }
        if (film.getName().isEmpty() || film.getName().isBlank()) {
            throw new ValidationException("Название фильма не должно быть пустым");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма не может ыть меньше 0");
        }
        if (film.getDescription().length() > 200 || film.getDescription().length() == 0) {
            throw new ValidationException("В описании неверное количество символов");
        }
        if (film.getId() <= 0) {
            film.setId(++id);
        }
    }
}