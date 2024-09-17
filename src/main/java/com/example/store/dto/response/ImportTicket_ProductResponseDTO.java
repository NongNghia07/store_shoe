package com.example.store.dto.response;

import com.example.store.entity.ImportTicket_Product;
import com.example.store.service.ProductService;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImportTicket_ProductResponseDTO {
    private UUID variantID;
    private UUID importTicketID;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private String productImageURL;
    private String productCategoryName;
    private Integer quantity;
    private Integer productQuantity;
    private String productColor;

    public ImportTicket_ProductResponseDTO(ImportTicket_Product importTicket_Product) {
        if(importTicket_Product.getId().getVariantID() != null)
            this.variantID = importTicket_Product.getId().getVariantID();
        if(importTicket_Product.getId().getImportTicketID() != null)
            this.importTicketID = importTicket_Product.getId().getImportTicketID();
        this.productColor = importTicket_Product.getProductVariant().getColor();
        this.productName = importTicket_Product.getProductVariant().getProduct().getName();
        this.productDescription = importTicket_Product.getProductVariant().getProduct().getDescription();
        this.productImageURL = importTicket_Product.getProductVariant().getImageUrl();
        this.quantity = importTicket_Product.getQuantity();
        this.productQuantity = importTicket_Product.getProductVariant().getQuantity();
        this.productCategoryName = importTicket_Product.getProductVariant().getProduct().getCategory().getName();
        this.productPrice = importTicket_Product.getProductVariant().getProduct().getPrice();
    }
}
