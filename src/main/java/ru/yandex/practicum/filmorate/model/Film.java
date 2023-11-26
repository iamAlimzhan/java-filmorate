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

    public void createGenre(Set<Genre> genres) {
        this.genres.addAll(genres);
    }
}
