package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.likes.LikesDbStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LikesDbTest {
    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;
    private final LikesDbStorage likesDbStorage;
    private final MpaDbStorage mpaDbStorage;

    @Test
    void addLike() {
        Film filmTest = addFilm();
        filmDbStorage.addFilm(filmTest);
        User user = addUser();
        userDbStorage.addUser(user);
        likesDbStorage.addLike(1, 1);

        List<Integer> likes = likesDbStorage.getFilmLikes(1);

        assertEquals(1, likes.size(), "список лайков не подходит");
    }

    @Test
    void deleteLikes() {
        Film filmTest = addFilm();
        filmDbStorage.addFilm(filmTest);
        User user = addUser();
        userDbStorage.addUser(user);
        likesDbStorage.addLike(1, 1);
        likesDbStorage.deleteLike(1, 1);

        List<Integer> likes = likesDbStorage.getFilmLikes(1);
        assertEquals(0, likes.size(), "список лайков не подходит");
    }

    @Test
    void getPopular() {
        Film firstFilm = addFilm();
        Film secondFilm = addFilmSeocnd();
        Film testFilm1 = filmDbStorage.addFilm(firstFilm);
        Film testFilm2 = filmDbStorage.addFilm(secondFilm);
        User user = addUser();
        User user1 = addUserSecond();
        userDbStorage.addUser(user);
        userDbStorage.addUser(user1);
        likesDbStorage.addLike(2, 1);
        likesDbStorage.addLike(2, 2);

        List<Film> popularFilms = likesDbStorage.getPopular(10);

        List<Film> testList = new ArrayList<>();
        testList.add(testFilm2);
        testList.add(testFilm1);

        assertEquals(testList, popularFilms, "список лайков не подходит");
    }

    @Test
    void getPopularWithoutFilm() {
        List<Film> popularFilms = likesDbStorage.getPopular(10);
        assertThat(popularFilms)
                .isNotNull()
                .isEqualTo(Collections.EMPTY_LIST);
    }

    private User addUser() {
        return User.builder().id(1).email("email@mail.ru").login("login").name("name")
                .birthday(LocalDate.of(1984, 2, 6)).build();
    }

    private User addUserSecond() {
        return User.builder().id(2).email("updated@mail.ru").login("loginUpdated").name("UpdatedName")
                .birthday(LocalDate.of(2001, 5, 5)).build();
    }

    private Film addFilm() {
        Mpa mpa = mpaDbStorage.getById(1);
        return Film.builder().id(1).name("FirstName").description("description")
                .releaseDate(LocalDate.of(2020, 10, 10)).duration(180)
                .mpa(mpa).build();
    }

    private Film addFilmSeocnd() {
        Mpa mpa = mpaDbStorage.getById(2);
        return Film.builder().id(1).name("SecondName").description("AfterUpdate")
                .releaseDate(LocalDate.of(2023, 5, 1)).duration(130).mpa(mpa).build();
    }
}
