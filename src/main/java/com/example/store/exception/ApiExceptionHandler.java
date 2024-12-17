package com.example.store.exception;

import com.example.store.util.MessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    // Xử lý ApiRequestException với thông báo đa ngôn ngữ
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiException(ApiRequestException e, Locale locale) {
        // Lấy trạng thái HTTP từ ErrorStatus của ngoại lệ
        HttpStatus status = e.getStatus().getStatus();

        ApiException apiException = new ApiException(
                MessageUtil.getMessage(e.getMessage()), // Sử dụng thông báo đa ngôn ngữ
                status,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        // Trả về response entity với payload
        return new ResponseEntity<>(apiException, status);
    }

    // Xử lý lỗi validation với MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidArgument(MethodArgumentNotValidException ex, Locale locale) {
        // Tạo map chứa các lỗi validation với thông báo chi tiết từng field
        Map<String, String> errorMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            // Lấy thông báo lỗi đa ngôn ngữ từ messages.properties
            errorMap.put(error.getField(), MessageUtil.getMessage(error.getDefaultMessage()));
        });

        ApiValidationException apiValidationException = new ApiValidationException(
                "Validation failed", // Thông báo tổng quát về lỗi validation
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z")),
                errorMap // Danh sách các lỗi chi tiết
        );

        return new ResponseEntity<>(apiValidationException, HttpStatus.BAD_REQUEST);
    }
}