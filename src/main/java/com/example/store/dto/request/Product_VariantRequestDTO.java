package com.example.store.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product_VariantRequestDTO {
    private UUID id;
    private Integer quantity;
    private Double price;
    private String size;
    private String color;
    private String imageUrl;
    private Boolean isStatus = true;
}
