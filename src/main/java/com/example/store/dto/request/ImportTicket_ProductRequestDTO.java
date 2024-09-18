package com.example.store.dto.request;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ImportTicket_ProductRequestDTO {
    private Product_VariantRequestDTO productVariant;
    private ImportTicketRequestDTO importTicket;
    private Integer quantity;
    private Double price;
}
