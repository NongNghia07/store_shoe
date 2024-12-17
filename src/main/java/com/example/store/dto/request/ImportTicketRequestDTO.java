package com.example.store.dto.request;

import lombok.*;

import java.util.List;
import java.util.UUID;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ImportTicketRequestDTO  {
    private UUID id;
    private Integer quantity;
    private Boolean isStatus = true;
    private List<ImportTicket_ProductRequestDTO> importTicketProducts;
    private SupplierRequestDTO supplier;
}
