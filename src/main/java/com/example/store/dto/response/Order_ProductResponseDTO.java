package com.example.store.dto.response;

import com.example.store.entity.Order_Product;
import com.example.store.service.ProductService;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order_ProductResponseDTO {
    private UUID orderId;
    private UUID variantId;
    private String productName;
    private Double unitPrice;
    private String productImageURL;
    private Integer quantity;
    private double totalPrice;

    public Order_ProductResponseDTO(Order_Product order_product) {
        if(order_product.getId().getOrderId() != null)
            this.orderId = order_product.getId().getOrderId();
        if(order_product.getId().getVariantId() != null)
            this.variantId = order_product.getId().getVariantId();
        this.productImageURL = order_product.getProductVariant().getImageUrl();
        this.quantity = order_product.getQuantity();
        this.unitPrice = order_product.getProductVariant().getPrice();
    }
}
