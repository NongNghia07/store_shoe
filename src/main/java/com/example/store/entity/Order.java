package com.example.store.entity;

import com.example.store.enums.OrderStatus;
import com.example.store.util.AutoGenerateCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@AutoGenerateCode(prefix = "ORD")
@Table(name = "`order`", uniqueConstraints = {@UniqueConstraint(columnNames = "code")})
public class Order extends BaseEntity implements Serializable {

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", nullable = false)
    @JsonIgnore
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vouchers_Id", nullable = true)
    @JsonIgnore
    private Vouchers voucher;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @JsonIgnore
    private Set<Order_Product> order_Products;

}
