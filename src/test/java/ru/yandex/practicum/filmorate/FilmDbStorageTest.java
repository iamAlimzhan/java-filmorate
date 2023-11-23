package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FilmDbStorageTest {
    private final FilmDbStorage filmStorage;
    private final GenreDbStorage genreDbStorage;
    private final MpaDbStorage mpaDbStorage;

    @Test
    void addWithId() {
        Film testFilm = addFilmTest();
        filmStorage.addFilm(testFilm);

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getFilmById(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    void addWithName() {
        Film testFilm = addFilmTest();
        filmStorage.addFilm(testFilm);

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getFilmById(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "TestName")
                );
    }

    @Test
    void addWithDesc() {
        Film testFilm = addFilmTest();
        filmStorage.addFilm(testFilm);

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getFilmById(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("description",
                                "description")
                );
    }

    @Test
    void addWithDate() {
        Film testFilm = addFilmTest();
        filmStorage.addFilm(testFilm);

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getFilmById(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("releaseDate",
                                LocalDate.of(2020, 10, 10))
                );
    }

    @Test
    void addWithDuration() {
        Film testFilm = addFilmTest();
        filmStorage.addFilm(testFilm);

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getFilmById(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("duration", 180)
                );
    }

    @Test
    void addWithMpa() {
        Film testFilm = addFilmTest();
        filmStorage.addFilm(testFilm);
        Mpa mpa = mpaDbStorage.getById(1);

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getFilmById(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film.getMpa()).isEqualTo(mpa)
                );
    }

    @Test
    void addWithGenre() {
        Film testFilm = addFilmTest();
        filmStorage.addFilm(testFilm);
        Set<Genre> genres = new HashSet<>();
        genres.add(genreDbStorage.getById(1));

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getFilmById(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film.getGenres()).isEqualTo(genres)
                );
    }

    @Test
    void updateFilmById() {
        Film testFilm = addFilmTest();
        filmStorage.addFilm(testFilm);
        Film filmForUpdate = addForUpdate();

        filmStorage.updateFilm(filmForUpdate);
        Optional<Film> filmOptionalUpdated = Optional.ofNullable(filmStorage.getFilmById(1));

        assertThat(filmOptionalUpdated)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    void updateWithName() {
        Film testFilm = addFilmTest();
        filmStorage.addFilm(testFilm);
        Film filmForUpdate = addForUpdate();

        filmStorage.updateFilm(filmForUpdate);
        Optional<Film> filmOptionalUpdated = Optional.ofNullable(filmStorage.getFilmById(1));

        assertThat(filmOptionalUpdated)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "AfterUpdateName")
                );
    }

    @Test
    void updateWithDesc() {
        Film testFilm = addFilmTest();
        filmStorage.addFilm(testFilm);
        Film filmForUpdate = addForUpdate();

        filmStorage.updateFilm(filmForUpdate);
        Optional<Film> filmOptionalUpdated = Optional.ofNullable(filmStorage.getFilmById(1));

        assertThat(filmOptionalUpdated)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("description",
                                "afterUpdateDescription")
                );
    }

    @Test
    void updateWithDuration() {
        Film testFilm = addFilmTest();
        filmStorage.addFilm(testFilm);
        Film filmForUpdate = addForUpdate();

        filmStorage.updateFilm(filmForUpdate);
        Optional<Film> filmOptionalUpdated = Optional.ofNullable(filmStorage.getFilmById(1));

        assertThat(filmOptionalUpdated)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("duration", 130)
                );
    }

    @Test
    void updateWithMpa() {
        Film testFilm = addFilmTest();
        filmStorage.addFilm(testFilm);
        Film filmForUpdate = addForUpdate();
        Mpa mpa = mpaDbStorage.getById(2);

        filmStorage.updateFilm(filmForUpdate);
        Optional<Film> filmOptionalUpdated = Optional.ofNullable(filmStorage.getFilmById(1));

        assertThat(filmOptionalUpdated)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film.getMpa()).isEqualTo(mpa)
                );
    }

    @Test
    void updateWithGenre() {
        Film testFilm = addFilmTest();
        filmStorage.addFilm(testFilm);
        Film filmForUpdate = addForUpdate();
        Set<Genre> testGenres = new HashSet<>();
        testGenres.add(genreDbStorage.getById(2));

        filmStorage.updateFilm(filmForUpdate);
        Optional<Film> filmOptionalUpdated = Optional.ofNullable(filmStorage.getFilmById(1));

        assertThat(filmOptionalUpdated)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film.getGenres()).isEqualTo(testGenres)
                );
    }

    @Test
    void getAllFilms() {
        Film film = addFilmTest();
        Film film1 = addForUpdate();
        film1.setId(2);
        filmStorage.addFilm(film);
        filmStorage.addFilm(film1);

        List<Film> allFilms = filmStorage.getAllFilms();

        assertEquals(2, allFilms.size());
    }

    private Film addFilmTest() {
        Mpa mpa = mpaDbStorage.getById(1);
        Film testFilm = Film.builder().id(1).name("TestName").description("description")
                .releaseDate(LocalDate.of(2020, 10, 10)).duration(180)
                .mpa(mpa).build();
        Set<Genre> genres = testFilm.getGenres();
        genres.add(genreDbStorage.getById(1));
        return testFilm;
    }

    private Film addForUpdate() {
        Mpa mpa = mpaDbStorage.getById(2);
        Film filmForUpdate = Film.builder().id(1).name("AfterUpdateName").description("afterUpdateDescription")
                .releaseDate(LocalDate.of(2023, 5, 1)).duration(130).mpa(mpa).build();
        Set<Genre> testGenres = filmForUpdate.getGenres();
        testGenres.add(genreDbStorage.getById(2));
        return filmForUpdate;
    }
}
