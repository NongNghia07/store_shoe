package com.example.store.entity;

import com.example.store.enums.DiscountType;
import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vouchers", uniqueConstraints = {@UniqueConstraint(columnNames = "value")})
public class Vouchers extends BaseEntity implements Serializable {

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType type; // %, fixed amount

    @Column(name = "value", nullable = false, unique = true)
    private Double value; // Value reduction

    @Column(name = "min_purchase_amount", nullable = true)
    private Double minPurchaseAmount; // Minimum purchase amount

    @Column(name = "max_discount", nullable = true)
    private Double maxDiscount; // Maximum discount amount if type is %

    @Column(name = "usage_limit", nullable = true)
    private Integer usageLimit; // Number of uses

    @Column(name = "user_limit", nullable = true)
    private Integer userLimit; // Number of times customer can use

    @Column(name = "start_date")
    private Instant startDate; // start date

    @Column(name = "end_date", nullable = true)
    private Instant endDate; // end date

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true; // active

}
