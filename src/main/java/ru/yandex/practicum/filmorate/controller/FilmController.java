package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.ValidationException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Integer id) {
        log.info("Получение фильма по id: {}", id);
        return filmService.getFilmById(id);
    }

    @GetMapping("/popular")
    public List<Film> getListOfPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        log.info("список из первых count: {} фильмов по количеству лайков", count);
        return filmService.getPopularFilms(count);
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) throws ValidationException {
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikeForFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Добавление лайка от пользоваеля userId: {} к фильму id: {}", userId, id);
        filmService.addLikeForFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Удаление лайка от пользователя userId: {} у фильма id: {}");
        filmService.deleteLikeForFilm(id, userId);
    }
}