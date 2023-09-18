package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    @JsonIgnore
    private Set<Integer> filmLikes;

    public void putLike(Integer userId) {
        filmLikes.add(userId);
    }

    public void deleteLike(Integer userId) {
        filmLikes.remove(userId);
    }

    public int getLikesQuantity() {
        return filmLikes.size();
    }

    // Добавим конструктор без аргументов для инициализации пустого множества filmLikes
    public Film() {
        filmLikes = new HashSet<>();
    }

    // Добавим геттер для filmLikes, чтобы было возможно получить его значение
    public Set<Integer> getFilmLikes() {
        return filmLikes;
    }
}
