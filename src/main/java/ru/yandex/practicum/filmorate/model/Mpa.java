package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class Mpa {
    @NotNull
    private int id;
    @NotNull
    private String name;

}
