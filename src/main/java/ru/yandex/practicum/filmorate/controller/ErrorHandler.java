package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.ErrorResponse;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {
            ObjectNotFoundException.class})
    public ErrorResponse handleObjectNotFoundException(final ObjectNotFoundException exp) {
        log.debug("Объект не найден, статус 404: {}", exp.getMessage(), exp);
        return new ErrorResponse("Object not found error");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRunTimeException(final RuntimeException exp) {
        log.error("Internal server error, статус 500: {}", exp.getMessage(), exp);
        return new ErrorResponse("Error");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException exp) {
        log.debug("Ошибка валидации, статус 400: {}", exp.getMessage(), exp);
        return new ErrorResponse("Validation error");
    }

    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable exp) {
        log.error("Internal server error, статус 500: {}", exp.getMessage(), exp);
        return new ErrorResponse("Internal server error");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final MethodArgumentNotValidException exp) {
        log.debug("Validation error, статус 400: {}", exp.getMessage(), exp);
        return new ErrorResponse("Validation error - Invalid input data");
    }
}