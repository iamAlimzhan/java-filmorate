package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friends.FriendsDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FriendsDbTest {
    private final FriendsDbStorage friendshipDbStorage;
    private final UserDbStorage userStorage;

    @Test
    void shouldAddFriend() {
        User user1 = createTestUser();
        User user2 = createSecondUser();
        userStorage.addUser(user1);
        userStorage.addUser(user2);
        friendshipDbStorage.add(1, 2);

        List<User> friendsOfUser1 = friendshipDbStorage.getFriendsList(1);
        List<User> friendsOfUser2 = friendshipDbStorage.getFriendsList(2);

        assertEquals(1, friendsOfUser1.size(), "Списки друзей не совпадают");
        assertEquals(0, friendsOfUser2.size(), "Списки друзей не совпадают");
    }

    @Test
    void shouldDeleteFriend() {
        User user1 = createTestUser();
        User user2 = createSecondUser();
        userStorage.addUser(user1);
        userStorage.addUser(user2);
        friendshipDbStorage.add(1, 2);
        friendshipDbStorage.delete(1, 2);

        List<User> friendsOfUser1 = friendshipDbStorage.getFriendsList(1);

        assertEquals(0, friendsOfUser1.size(), "Списки не совпадают");
    }

    @Test
    void shouldGetCommonFriends() {
        User user1 = createTestUser();
        User user2 = createSecondUser();
        User user3 = createThirdUser();
        userStorage.addUser(user1);
        userStorage.addUser(user2);
        userStorage.addUser(user3);
        friendshipDbStorage.add(1, 3);
        friendshipDbStorage.add(2, 3);

        List<User> commonFriends = friendshipDbStorage.getMutualFriends(1, 2);

        assertEquals(userStorage.getById(3), commonFriends.get(0), "Списки общиз друзей не совпадают");

    }

    private User createTestUser() {
        return User.builder().id(1).email("1@mail.ru").login("login1").name("name1")
                .birthday(LocalDate.of(1984, 2, 6)).build();
    }

    private User createSecondUser() {
        return User.builder().id(2).email("2@mail.ru").login("login2").name("name2")
                .birthday(LocalDate.of(2000, 2, 20)).build();
    }

    private User createThirdUser() {
        return User.builder().id(3).email("3@mail.ru").login("login3").name("name3")
                .birthday(LocalDate.of(1812, 2, 10)).build();
    }
}
