package com.example.store.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MtTranslationsRequestDTO {
    @NotBlank
    private String tableName;
    @NotBlank
    private String columnName;
    private UUID rowId;
    @NotBlank
    @Max(10)
    private String languageCode;
    @NotBlank
    private String translation;
}
