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
    private String productImageURL;
    private String productCategoryName;
    private Integer quantity;
    private Double price;
    private String productColor;
    private String productSize;

    public ImportTicket_ProductResponseDTO(ImportTicket_Product importTicket_Product) {
        this.productImageURL = importTicket_Product.getProductVariant().getImageUrl();
        this.quantity = importTicket_Product.getQuantity();
        this.price = importTicket_Product.getPrice();
    }
}
