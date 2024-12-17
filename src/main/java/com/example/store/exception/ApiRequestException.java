package com.example.store.exception;


import com.example.store.enums.ErrorStatus;

public class ApiRequestException extends RuntimeException {
    private final ErrorStatus status;

    public ApiRequestException(String message, ErrorStatus status) {
        super(message);
        this.status = status;
    }

    public ApiRequestException(String message, ErrorStatus status, Throwable throwable) {
        super(message, throwable);
        this.status = status;
    }

    public ErrorStatus getStatus() {
        return status;
    }
}
