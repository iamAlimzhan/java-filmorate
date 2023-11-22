package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Friends {
    @NotNull
    private int id;
    @NotNull
    private int friendId;
    @NotNull
    private boolean isConfirm;
}
