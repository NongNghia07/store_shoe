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
    private String code;
    private Integer quantity;
    private Boolean isStatus;
    private List<ImportTicket_ProductRequestDTO> importTicketProductRequestDTOs;
    private List<SupplierRequestDTO> supplierRequestDTOs;
}
