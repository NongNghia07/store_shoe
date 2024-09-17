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
    private UUID orderID;
    private UUID variantID;
    private String productName;
    private Double productPrice;
    private String productImageURL;
    private Integer quantity;
    private Integer productQuantity;

    public Order_ProductResponseDTO(Order_Product order_product) {
        if(order_product.getId().getOrderID() != null)
            this.orderID = order_product.getId().getOrderID();
        if(order_product.getId().getVariantID() != null)
            this.variantID = order_product.getId().getVariantID();
        this.productImageURL = order_product.getProductVariant().getImageUrl();
        this.productName = order_product.getProductVariant().getProduct().getName();
        this.quantity = order_product.getQuantity();
        this.productQuantity = order_product.getProductVariant().getQuantity();
        this.productPrice = order_product.getProductVariant().getPrice();
    }
}
