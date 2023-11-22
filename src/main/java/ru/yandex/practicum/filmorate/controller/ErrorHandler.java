package ru.yandex.practicum.filmorate.controller;

<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.ErrorResponse;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

@RestControllerAdvice
public class ErrorHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ErrorResponse handleUserNotFoundException(final UserNotFoundException exp) {
        return new ErrorResponse("Object not found error");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {
            ObjectNotFoundException.class})
    public ErrorResponse handleObjectNotFoundException(final ObjectNotFoundException exp) {
        return new ErrorResponse("Object not found error");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {
            FilmNotFoundException.class})
    public ErrorResponse handleFilmNotFoundException(final FilmNotFoundException exp) {
        return new ErrorResponse("Object not found error");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRunTimeException(final RuntimeException exp) {
=======
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.filmorate.ErrorResponse;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

@ControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRunTimeException(final RuntimeException exp) {
        log.error("Internal Server Error {}", exp.getMessage(), exp);
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
        return new ErrorResponse("Error");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException exp) {
<<<<<<< HEAD
        return new ErrorResponse("Validation error");
    }

}
=======
        log.error("Validation Error {}", exp.getMessage(), exp);
        return new ErrorResponse("Validation error");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException exp) {
        log.error("Object Not Found Error {}", exp.getMessage(), exp);
        return new ErrorResponse("Object not found error");
    }
}
>>>>>>> 456a10b75a6fb34452ab54527af13ff869610b08
