package com.example.store.dto.request;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ImportTicket_ProductRequestDTO {
    private UUID variantId;
    private UUID importTicketID;
    private Integer quantity;
}
