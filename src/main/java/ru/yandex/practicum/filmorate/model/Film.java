package ru.yandex.practicum.filmorate.model;

<<<<<<< HEAD
import lombok.Builder;
=======
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
<<<<<<< HEAD
    @NotNull
    private Mpa mpa;
    private final Set<Genre> genres = new HashSet<>();
    private final Set<Integer> filmLikes = new HashSet<>();

    public void createGenre(Genre genre) {
        genres.add(genre);
    }

    public void putLike(int userId) {
        filmLikes.add(userId);
    }

=======
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
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
}
