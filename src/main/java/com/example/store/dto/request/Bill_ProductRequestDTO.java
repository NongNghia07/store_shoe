package com.example.store.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bill_ProductRequestDTO {
    private UUID variantID;
    private UUID billID;
    private Integer quantity;
}
