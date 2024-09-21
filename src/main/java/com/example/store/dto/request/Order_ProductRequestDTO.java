package com.example.store.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order_ProductRequestDTO {
    private OrderRequestDTO order;
    private Product_VariantRequestDTO productVariant;
    private Integer quantity;
    private Double unitPrice;
}
