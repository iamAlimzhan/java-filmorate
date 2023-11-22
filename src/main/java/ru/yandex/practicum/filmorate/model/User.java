package ru.yandex.practicum.filmorate.model;

<<<<<<< HEAD
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;
=======
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08

@Data
@Builder
public class User {
    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
<<<<<<< HEAD
    private final Set<Integer> friends = new TreeSet<>();
=======
    @JsonIgnore
    private Set<Integer> friends;

    public int getFriendsQuantity() {
        return friends.size();
    }

    public void addFriend(Integer id) {
        friends.add(id);
    }

    public void removeFriend(Integer id) {
        friends.remove(id);
    }

    // Добавим конструктор без аргументов для инициализации пустого множества friends
    public User() {
        friends = new HashSet<>();
    }

    // Добавим геттер для friends, чтобы было возможно получить его значение
    public Set<Integer> getFriends() {
        return friends;
    }
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
}
