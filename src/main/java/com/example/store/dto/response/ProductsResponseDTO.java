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
        this.name = products.getName();
        this.quantity = products.getQuantity();
        this.price = products.getPrice();
        this.brand = products.getBrand();
        this.description = products.getDescription();
        this.image_URL = products.getImageURL();
        this.isStatus = products.getIsStatus();
        if(products.getCategory() != null)
            this.category = new CategoriesResponseDTO(products.getCategory());
    }

    private Set<Product_VariantsResponseDTO> convProductVariantResponseDTOs(Set<Product_Variants> productVariants) {
        Set<Product_VariantsResponseDTO> productVariantsResponseDTOs = new HashSet<>();
        for (Product_Variants variant : productVariants) {
            Product_VariantsResponseDTO variantDTO = new Product_VariantsResponseDTO(variant);
            productVariantsResponseDTOs.add(variantDTO);
        }
        return productVariantsResponseDTOs;
    }

    private Set<ImportTicket_ProductResponseDTO> convertImportTicketProductDTOs(Set<ImportTicket_Product> importTicketProducts) {
        Set<ImportTicket_ProductResponseDTO> importTicketProductDTOs = new HashSet<>();
        for (ImportTicket_Product importTicketProduct : importTicketProducts) {
            ImportTicket_ProductResponseDTO importTicketProductDTO = new ImportTicket_ProductResponseDTO(importTicketProduct);
            importTicketProductDTOs.add(importTicketProductDTO);
        }
        return importTicketProductDTOs;
    }

    private Set<Order_ProductResponseDTO> convertOrderProductDTOs(Set<Order_Product> orderProducts) {
        Set<Order_ProductResponseDTO> orderProductDTOs = new HashSet<>();
        for (Order_Product orderProduct : orderProducts) {
            Order_ProductResponseDTO orderProductDTO = new Order_ProductResponseDTO(orderProduct);
            orderProductDTOs.add(orderProductDTO);
        }
        return orderProductDTOs;
    }
}
