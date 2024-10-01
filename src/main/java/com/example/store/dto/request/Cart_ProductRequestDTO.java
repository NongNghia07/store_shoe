package com.example.store.dto.request;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cart_ProductRequestDTO {
    private CartRequestDTO cart;
    private Product_VariantRequestDTO productVariant;
    private Integer quantity;
}
