package com.example.store.entity;

import com.example.store.entity.embeddable.ImportTicket_ProductKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "importTicket_Product")
public class ImportTicket_Product implements Serializable {
    @EmbeddedId
    private ImportTicket_ProductKey id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("variantId")
    @JsonIgnore
    private Product_Variants productVariant;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("importTicketId")
    @JsonIgnore
    private Import_Ticket importTicket;
}
