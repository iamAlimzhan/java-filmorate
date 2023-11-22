package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDbStorageTest {
    private final UserDbStorage userStorage;

    @Test
    void addWithId() {
        User userForTest = buildUserTest();
        userStorage.addUser(userForTest);
        Optional<User> userOptional = Optional.ofNullable(userStorage.getById(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    void addWithEmail() {
        User userForTest = buildUserTest();
        userStorage.addUser(userForTest);
        Optional<User> userOptional = Optional.ofNullable(userStorage.getById(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("email", "email@mail.ru")
                );
    }

    @Test
    void addWithLogin() {
        User userForTest = buildUserTest();
        userStorage.addUser(userForTest);
        Optional<User> userOptional = Optional.ofNullable(userStorage.getById(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("login", "login")
                );
    }

    @Test
    void addWithName() {
        User userForTest = buildUserTest();
        userStorage.addUser(userForTest);
        Optional<User> userOptional = Optional.ofNullable(userStorage.getById(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("name", "name")
                );
    }

    @Test
    void addWithBirthday() {
        User userForTest = buildUserTest();
        userStorage.addUser(userForTest);
        Optional<User> userOptional = Optional.ofNullable(userStorage.getById(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("birthday",
                                LocalDate.of(1984, 2, 6))
                );
    }

    @Test
    void updateWithId() {
        User userForTest = buildUserTest();
        userStorage.addUser(userForTest);
        User forUpdate = buildForUpdate();
        userStorage.updateUser(forUpdate);
        Optional<User> updateUser = Optional.ofNullable(userStorage.getById(1));

        assertThat(updateUser)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    void updateWithEmail() {
        User userForTest = buildUserTest();
        userStorage.addUser(userForTest);
        User forUpdate = buildForUpdate();
        userStorage.updateUser(forUpdate);
        Optional<User> updateUser = Optional.ofNullable(userStorage.getById(1));

        assertThat(updateUser)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("email", "updated@mail.ru")
                );
    }

    @Test
    void updateWithLogin() {
        User userForTest = buildUserTest();
        userStorage.addUser(userForTest);
        User forUpdate = buildForUpdate();
        userStorage.updateUser(forUpdate);
        Optional<User> updateUser = Optional.ofNullable(userStorage.getById(1));

        assertThat(updateUser)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("login", "UpdatedLogin")
                );
    }

    @Test
    void updateWithName() {
        User userForTest = buildUserTest();
        userStorage.addUser(userForTest);
        User forUpdate = buildForUpdate();
        userStorage.updateUser(forUpdate);
        Optional<User> updateUser = Optional.ofNullable(userStorage.getById(1));

        assertThat(updateUser)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("name", "UpdateName")
                );
    }

    @Test
    void updateWithBirthday() {
        User userForTest = buildUserTest();
        userStorage.addUser(userForTest);
        User forUpdate = buildForUpdate();
        userStorage.updateUser(forUpdate);
        Optional<User> updateUser = Optional.ofNullable(userStorage.getById(1));

        assertThat(updateUser)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("birthday",
                                LocalDate.of(2000, 5, 5))
                );
    }

    @Test
    void getAll() {
        User user = buildUserTest();
        User user1 = buildForUpdate();
        user1.setId(2);
        userStorage.addUser(user);
        userStorage.addUser(user1);

        List<User> allUsers = userStorage.getAll();

        assertEquals(2, allUsers.size(), "Список пользователей не соответствует ожидаемому");
    }

    private User buildUserTest() {
        return User.builder().id(1).email("email@mail.ru").login("login").name("name")
                .birthday(LocalDate.of(1984, 2, 6)).build();
    }

    private User buildForUpdate() {
        return User.builder().id(1).email("updated@mail.ru").login("UpdatedLogin").name("UpdateName")
                .birthday(LocalDate.of(2000, 5, 5)).build();
    }
}
