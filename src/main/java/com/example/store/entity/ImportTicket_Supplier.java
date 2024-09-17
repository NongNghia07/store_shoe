package com.example.store.entity;

import com.example.store.entity.embeddable.ImportTicket_SupplierKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "importTicket_Supplier")
public class ImportTicket_Supplier implements Serializable {
    @EmbeddedId
    private ImportTicket_SupplierKey id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("importTicketID")
//    @JoinColumn(name = "importTicket_ID")
    @JsonIgnore
    private Import_Ticket importTicket;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("supplierID")
//    @JoinColumn(name = "supplier_ID")
    @JsonIgnore
    private Supplier supplier;
}
