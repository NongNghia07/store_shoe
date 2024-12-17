package com.example.store.enums;

import org.springframework.http.HttpStatus;

public enum ErrorStatus {
    BAD_REQUEST_400(HttpStatus.BAD_REQUEST),        // 400 Bad Request
    FORBIDDEN_403(HttpStatus.FORBIDDEN),            // 403 Forbidden
    UNAUTHORIZED_401(HttpStatus.UNAUTHORIZED);      // 401 Unauthorized

    private final HttpStatus status;

    ErrorStatus(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
