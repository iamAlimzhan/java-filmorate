package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

<<<<<<< HEAD
import javax.validation.Valid;
=======
import javax.validation.ValidationException;
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
<<<<<<< HEAD

    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
=======

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
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
        return filmService.getFilmById(id);
    }

    @GetMapping("/popular")
<<<<<<< HEAD
    public List<Film> getPopular(@RequestParam(defaultValue = "10") int count) {
=======
    public List<Film> getListOfPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        log.info("список из первых count: {} фильмов по количеству лайков", count);
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
        return filmService.getPopularFilms(count);
    }

    @PostMapping
<<<<<<< HEAD
    public Film addFilm(@Valid @RequestBody Film film) {
=======
    public Film addFilm(@RequestBody Film film) throws ValidationException {
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
        return filmService.addFilm(film);
    }

    @PutMapping
<<<<<<< HEAD
    public Film updateFilm(@Valid @RequestBody Film film) {
=======
    public Film updateFilm(@RequestBody Film film) {
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
<<<<<<< HEAD
    public void addLike(@PathVariable("id") int filmId, @PathVariable int userId) {
        filmService.addLikes(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") int filmId, @PathVariable int userId) {
        filmService.deleteLikes(filmId, userId);
=======
    public void addLikeForFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Добавление лайка от пользоваеля userId: {} к фильму id: {}", userId, id);
        filmService.addLikeForFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Удаление лайка от пользователя userId: {} у фильма id: {}");
        filmService.deleteLikeForFilm(id, userId);
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
    }
}