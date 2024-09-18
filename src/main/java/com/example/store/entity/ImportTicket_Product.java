package com.example.store.entity;

import com.example.store.entity.embeddable.ImportTicket_ProductKey;
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
@Table(name = "importTicket_Product")
public class ImportTicket_Product implements Serializable {
    @EmbeddedId
    private ImportTicket_ProductKey id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("variantID")
//    @JoinColumn(name = "variant_ID")
    @JsonIgnore
    private Product_Variants productVariant;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("importTicketID")
//    @JoinColumn(name = "importTicket_ID")
    @JsonIgnore
    private Import_Ticket importTicket;
}
