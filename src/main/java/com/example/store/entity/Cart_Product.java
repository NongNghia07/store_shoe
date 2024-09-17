package com.example.store.entity;

import com.example.store.entity.embeddable.Cart_ProductKey;
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
@Table(name = "cart_product")
public class Cart_Product implements Serializable {
    @EmbeddedId
    private Cart_ProductKey id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("variantID")
//    @JoinColumn(name = "variant_ID")
    @JsonIgnore
    private Product_Variants productVariant;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cartID")
//    @JoinColumn(name = "cart_ID")
    @JsonIgnore
    private Cart cart;
}
