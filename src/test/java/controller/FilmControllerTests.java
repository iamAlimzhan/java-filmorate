package controller;

import model.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.FilmorateApplication;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(classes = FilmorateApplication.class,webEnvironment = DEFINED_PORT)
public class FilmControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1/films";
    }

    @Test
    public void testAddValidFilm() {
        Film newFilm = new Film(1, "Film", "Description", LocalDate.of(2022, 5,
                10), 90);

        ResponseEntity<Film> response = restTemplate.postForEntity(baseUrl, newFilm, Film.class);

        // Проверяем код состояния HTTP
        assertEquals(200, response.getStatusCodeValue());

        Film addedFilm = response.getBody();
        assertEquals(newFilm.getName(), addedFilm.getName());
    }

    @Test
    public void testAddInvalidFilm() {
        Film invalidFilm = new Film(0, "Invalid Film", "Description", LocalDate.of(1800,
                1, 1), -10);

        ResponseEntity<Film> response = restTemplate.postForEntity(baseUrl, invalidFilm, Film.class);

        // Проверяем код состояния HTTP
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testGetAllFilms() {
        ResponseEntity<Film> response = restTemplate.getForEntity(baseUrl, Film.class);

        // Проверяем код состояния HTTP
        assertEquals(200, response.getStatusCodeValue());

        Film film = response.getBody();
    }

}
