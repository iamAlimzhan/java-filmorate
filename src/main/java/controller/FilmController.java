package controller;

import lombok.extern.slf4j.Slf4j;
import model.Film;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/films")
@Validated
@Slf4j
public class FilmController {
    private List<Film> films = new ArrayList<>();
    private int nextFilmId = 1;

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Создан новый фильм: {}", film.getName());
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        film.setId(nextFilmId++);
        films.add(film);
        return film;
    }

    @PutMapping("/{id}")
    public Film updateFilm(@PathVariable int id, @Valid @RequestBody Film updatedFilm) {
        log.info("Обновлен новый фильм: {}", updatedFilm.getName());
        if (updatedFilm.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
        if (updatedFilm.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        for (Film film : films) {
            if (film.getId() == id) {
                film.setName(updatedFilm.getName());
                film.setDescription(updatedFilm.getDescription());
                film.setReleaseDate(updatedFilm.getReleaseDate());
                film.setDuration(updatedFilm.getDuration());
                return film;
            }
        }
        return null; // Фильм не найден
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Получен новый фильм: {}", films);
        return films;
    }
}
