package com.example.store.entity;

import com.example.store.entity.embeddable.Bill_ProductKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "bill_product")
public class Bill_Product implements Serializable {
    @EmbeddedId
    private Bill_ProductKey id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "is_status")
    private Boolean isStatus = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("variantID")
//    @JoinColumn(name = "variant_ID")
    @JsonIgnore
    private Product_Variants productVariant;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("billID")
//    @JoinColumn(name = "bill_ID")
    @JsonIgnore
    private Bill bill;
}
