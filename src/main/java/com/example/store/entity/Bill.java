package com.example.store.entity;

import com.example.store.enums.BillStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bill")
public class Bill extends BaseEntity implements Serializable {

    @Column(name = "totalAmount")
    private Double totalAmount;

    @Column(name = "tax_amount")
    private Double taxAmount;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "net_amount")
    private Double netAmount;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BillStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_ID")
    @JsonIgnore
    private Order order;
}
