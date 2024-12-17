package com.example.store.entity;

import com.example.store.enums.BillStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "bill")
public class Bill extends BaseEntity implements Serializable {

    @Column(name = "totalAmount", nullable = false) // Không được null
    private Double totalAmount;

    @Column(name = "tax_amount", nullable = true) // Có thể null
    private Double taxAmount;

    @Column(name = "discount", nullable = true) // Có thể null
    private Double discount;

    @Column(name = "net_amount", nullable = false) // Không được null
    private Double netAmount;

    @Column(name = "status", nullable = false) // Không được null
    @Enumerated(EnumType.STRING) // Lưu trạng thái Bill dưới dạng chuỗi
    private BillStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_Id", nullable = false) // Không được null
    @JsonIgnore
    private Order order;

}