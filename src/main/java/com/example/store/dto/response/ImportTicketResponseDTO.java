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
        this.creator = importTicket.getCreator();
        this.createDate = importTicket.getCreateDate();
        this.isStatus = importTicket.getIsStatus();
        this.supplierDTO = convertSupplierDTO(importTicket.getSupplier());
    }

    private Set<ImportTicket_ProductResponseDTO> convertImportTicketProductDTO(Set<ImportTicket_Product> importTicketProducts){
        Set<ImportTicket_ProductResponseDTO> importTicketProductDTOs = new HashSet<>();
        for(ImportTicket_Product importTicketProduct : importTicketProducts){
            ImportTicket_ProductResponseDTO importTicketProductDTO = new ImportTicket_ProductResponseDTO(importTicketProduct);
            importTicketProductDTOs.add(importTicketProductDTO);
        }
        return importTicketProductDTOs;
    }

    private SupplierResponseDTO convertSupplierDTO(Supplier supplier){
        SupplierResponseDTO supplierDTO = new SupplierResponseDTO();
        supplierDTO.setId(supplier.getId());
        supplierDTO.setName(supplier.getName());
        return supplierDTO;
    }
}
