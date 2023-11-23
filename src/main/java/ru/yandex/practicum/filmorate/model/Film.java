package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
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

}
