package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    @JsonIgnore
    private Set<Integer> friends = new HashSet<>();

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
}
