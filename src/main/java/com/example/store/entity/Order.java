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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`order`")
public class Order implements Serializable {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "creator")
    private Integer creator;

    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime createDate;

    @Column(name = "updater")
    private Integer updater;

    @Column(name = "update_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime updateDate;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_ID")
    @JsonIgnore
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vouchers_ID")
    @JsonIgnore
    private Vouchers voucher;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @JsonIgnore
    private Set<Order_Product> order_Products;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID(); // Gán UUID thủ công trước khi lưu
        }
    }
}