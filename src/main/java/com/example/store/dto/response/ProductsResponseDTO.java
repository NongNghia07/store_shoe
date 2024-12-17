package com.example.store.dto.response;


import com.example.store.entity.*;
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
public class ProductsResponseDTO {
    private UUID id;
    private String name;
    private Integer quantity;
    private Double price;
    private String brand;
    private String description;
    private Integer creator;
    private LocalDateTime createDate;
    private Integer updater;
    private LocalDateTime updateDate;
    private String image_URL;
    private Boolean isStatus = true;
    private Set<Product_VariantsResponseDTO> productVariantsResponseDTOs;
    private CategoriesResponseDTO category;

    public ProductsResponseDTO(Products products) {
        if(products.getId() != null)
            this.id = products.getId();
        this.quantity = products.getQuantity();
        this.price = products.getPrice();
        this.image_URL = products.getImageURL();
        this.isStatus = products.getIsStatus();
        if(products.getCategory() != null)
            this.category = new CategoriesResponseDTO(products.getCategory());
    }

}
