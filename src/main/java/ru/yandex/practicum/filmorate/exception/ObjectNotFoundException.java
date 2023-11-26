package ru.yandex.practicum.filmorate.exception;

public class ObjectNotFoundException extends IllegalArgumentException {
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
