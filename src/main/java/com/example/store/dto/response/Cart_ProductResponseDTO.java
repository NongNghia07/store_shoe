package com.example.store.dto.response;

import com.example.store.entity.Cart_Product;
import com.example.store.service.ProductService;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart_ProductResponseDTO {
    private UUID variantID;
    private UUID cartID;
    private String productName;
    private Double productPrice;
    private Integer productQuantity;
    private Integer quantity;
    private Boolean productStatus;
    private String productImageURL;

    public Cart_ProductResponseDTO(Cart_Product cart_product) {
        if(cart_product.getId().getCartID() != null)
            this.cartID = cart_product.getId().getCartID();
        if(cart_product.getId().getVariantID() != null)
            this.variantID = cart_product.getId().getVariantID();
        this.productQuantity = cart_product.getProductVariant().getQuantity();
        this.productStatus = cart_product.getProductVariant().getStatus();
        this.productImageURL = cart_product.getProductVariant().getImageUrl();
        this.quantity = cart_product.getQuantity();
        this.productName = cart_product.getProductVariant().getProduct().getName();
        this.productPrice = cart_product.getProductVariant().getPrice();
    }
}
