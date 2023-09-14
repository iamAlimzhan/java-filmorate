package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(classes = FilmorateApplication.class, webEnvironment = DEFINED_PORT)
public class UserControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testCreateUserValidation() throws Exception {
        User invalidUser = new User(0, "invalid-email", "login with space", "Name",
                LocalDate.now().plusDays(1), new HashSet<>());

        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/users", invalidUser, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateUserSuccess() throws Exception {
        User validUser = new User(0, "valid@email.com", "validlogin", "Valid Name",
                LocalDate.now(), new HashSet<>());

        ResponseEntity<User> response = restTemplate.postForEntity("/users", validUser, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        User createdUser = response.getBody();
        assertEquals("Valid Name", createdUser.getName());
    }

}


