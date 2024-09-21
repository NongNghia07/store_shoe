package com.example.store.entity;

import com.example.store.entity.embeddable.Order_ProductKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "order_product")
public class Order_Product implements Serializable {
    @EmbeddedId
    private Order_ProductKey id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total_price")
    private Double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("variantID")
//    @JoinColumn(name = "variant_ID")
    @JsonIgnore
    private Product_Variants productVariant;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderID")
//    @JoinColumn(name = "order_ID")
    @JsonIgnore
    private Order order;
}
