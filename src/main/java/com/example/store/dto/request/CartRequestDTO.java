package com.example.store.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDTO {
    private UUID id;
    private UUID userID;
    private Double price;
    private Integer quantity;
    private Boolean isStatus;
    private ProductsRequestDTO productRequestDTO;
}
