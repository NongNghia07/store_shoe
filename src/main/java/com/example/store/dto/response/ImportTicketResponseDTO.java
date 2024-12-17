package com.example.store.dto.response;

import com.example.store.entity.Import_Ticket;
import com.example.store.entity.ImportTicket_Product;
import com.example.store.entity.Supplier;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ImportTicketResponseDTO {
    private UUID id;
    private String code;
    private Integer quantity;
    private Integer creator;
    private LocalDateTime createDate;
    private Boolean isStatus;
    private Set<ImportTicket_ProductResponseDTO> importTicketProductDTOs;
    private SupplierResponseDTO supplierDTO;

    public ImportTicketResponseDTO(Import_Ticket importTicket){
        if(importTicket.getId() != null)
            this.id = importTicket.getId();
        this.code = importTicket.getCode();
        this.quantity = importTicket.getQuantity();
        this.isStatus = importTicket.getIsStatus();
    }
}
