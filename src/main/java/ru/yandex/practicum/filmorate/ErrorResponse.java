package ru.yandex.practicum.filmorate;

public class ErrorResponse {
    private String errorResponse;

    public ErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }

    public String getErrorResponse() {
        return errorResponse;
    }
}
