package ru.yandex.practicum.filmorate.model;

<<<<<<< HEAD
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Builder
@Data
public class Genre {
    @NotNull
    private int id;
    @NotNull
    private String name;

=======
public enum Genre {
    COMEDY,
    DRAMA,
    CARTOON,
    THRILLER,
    DOCUMENTARY,
    ACTION
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
}
