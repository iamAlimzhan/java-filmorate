package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
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
}
