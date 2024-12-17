package com.example.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_variants")
public class Product_Variants extends BaseEntity implements Serializable {
    @Id
    @Column(columnDefinition = "CHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    @Column(name = "is_status", nullable = false)
    private Boolean isStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_Id", nullable = false)
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
    private Set<Cart_Product> cartProducts;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID(); // Gán UUID thủ công trước khi lưu
        }
    }
}
