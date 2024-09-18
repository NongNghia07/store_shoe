package com.example.store.dto.response;

import com.example.store.entity.Supplier;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponseDTO {
    private UUID id;
    private String name;

    public SupplierResponseDTO(Supplier supplier) {
        if(supplier.getId() != null)
            this.id = supplier.getId();
        this.name = supplier.getName();
    }
}
