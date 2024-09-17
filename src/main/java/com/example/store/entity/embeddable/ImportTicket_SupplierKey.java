package com.example.store.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ImportTicket_SupplierKey implements Serializable {
    @Column(name = "importTicket_ID", columnDefinition = "CHAR(36)")
    private UUID importTicketID;

    @Column(name = "supplier_ID", columnDefinition = "CHAR(36)")
    private UUID supplierID;
}
