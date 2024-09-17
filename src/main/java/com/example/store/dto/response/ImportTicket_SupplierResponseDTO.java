package com.example.store.dto.response;

import com.example.store.entity.ImportTicket_Supplier;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImportTicket_SupplierResponseDTO {
    private UUID supplierID;
    private UUID ImportTicketID;
    private String supplierName;

    public ImportTicket_SupplierResponseDTO(ImportTicket_Supplier importTicket_Supplier) {
        if(importTicket_Supplier.getId().getSupplierID() != null)
            this.supplierID = importTicket_Supplier.getId().getSupplierID();
        if(importTicket_Supplier.getId().getImportTicketID() != null)
            this.ImportTicketID = importTicket_Supplier.getId().getImportTicketID();
        this.supplierName = importTicket_Supplier.getSupplier().getName();
    }
}
