package com.example.store.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoriesRequestDTO {
    private UUID id;
    private Boolean isStatus = true;
}
