package com.example.store.dto.response;


import com.example.store.entity.*;
import com.example.store.service.ProductService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product_VariantsResponseDTO {
    private UUID id;
    private Integer quantity;
    private Double price;
    private String size;
    private String color;
    private Integer creator;
    private LocalDateTime createDate;
    private Integer updater;
    private LocalDateTime updateDate;
    private String imageUrl;
    private Boolean status = true;
    private ProductsResponseDTO productResponseDTO;

    public Product_VariantsResponseDTO(Product_Variants productVariant){
        this.id = productVariant.getId();
        this.quantity = productVariant.getQuantity();
        this.price = productVariant.getPrice();
        this.imageUrl = productVariant.getImageUrl();
        if(productVariant.getProduct()!= null)
            this.productResponseDTO = new ProductsResponseDTO(productVariant.getProduct());
    }
}
