package com.example.store.entity;

import com.example.store.enums.OrderStatus;
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
public class Order extends BaseEntity implements Serializable {

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "delivery_address")
    private String deliveryAddress;

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

}
