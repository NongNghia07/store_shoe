package com.example.store.dto.response.util;

import com.example.store.util.MessageUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@JsonDeserialize
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponseDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ServiceResponseDTO<T> success(HttpStatus status, String messageCode, T data) {
        // Sử dụng MessageUtil để lấy thông báo đa ngôn ngữ từ messageCode
        String localizedMessage = MessageUtil.getMessage(messageCode);
        return new ServiceResponseDTO<>(status.value(), localizedMessage, data);
    }

}
