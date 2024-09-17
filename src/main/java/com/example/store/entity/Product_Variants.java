package com.example.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_variants")
public class Product_Variants implements Serializable {
    @Id
    @Column(columnDefinition = "CHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "size")
    private String size;

    @Column(name = "color")
    private String color;

    @Column(name = "creator")
    private Integer creator;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "updater")
    private Integer updater;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "status")
    private Boolean status = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_ID")
    @JsonIgnore
    private Products product;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productVariant")
    @JsonIgnore
    private Set<ImportTicket_Product> importTicketProducts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productVariant")
    @JsonIgnore
    private Set<Order_Product> orderProducts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productVariant")
    @JsonIgnore
    private Set<Bill_Product> billProducts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productVariant")
    @JsonIgnore
    private Set<Cart_Product> cartProducts;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID(); // Gán UUID thủ công trước khi lưu
        }
    }
}
