package com.example.store.dto.request;


import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductsRequestDTO {
    private UUID id;
    private String name;
    private Integer quantity;
    private Double price;
    private String brand;
    private String description;
    private String image_URL;
    private Boolean isStatus = true;
    private List<Product_VariantRequestDTO> productVariants;
    private CategoriesRequestDTO category;
}
