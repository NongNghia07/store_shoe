package com.example.store.dto.response;

import com.example.store.entity.Bill_Product;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Bill_ProductResponseDTO {
    private UUID billID;
    private UUID variantID;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private String productImageURL;
    private Integer quantity;
    private Integer productQuantity;
    private String productColor;
    private String productSize;
    private String productBrand;

    public Bill_ProductResponseDTO(Bill_Product bill_product) {
        if(bill_product.getBill().getId() != null)
            this.billID = bill_product.getBill().getId();
        if(bill_product.getProductVariant().getId() != null)
            this.variantID = bill_product.getProductVariant().getId();
        this.productName = bill_product.getProductVariant().getProduct().getName();
        this.productDescription = bill_product.getProductVariant().getProduct().getDescription();
        this.productBrand = bill_product.getProductVariant().getProduct().getBrand();
        this.productColor = bill_product.getProductVariant().getColor();
        this.productImageURL = bill_product.getProductVariant().getProduct().getImageURL();
        this.productQuantity = bill_product.getProductVariant().getQuantity();
        this.productSize = bill_product.getProductVariant().getSize();
        this.quantity = bill_product.getQuantity();
        this.productPrice = bill_product.getProductVariant().getPrice();
    }
}
