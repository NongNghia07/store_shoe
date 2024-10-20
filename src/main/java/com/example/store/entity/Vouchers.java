package com.example.store.entity;

import com.example.store.enums.DiscountType;
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
@Table(name = "vouchers")
public class Vouchers extends BaseEntity implements Serializable {

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DiscountType type; // %, số tiền cố định

    @Column(name = "value")
    private Double value;

    @Column(name = "min_purchase_amount")
    private Double minPurchaseAmount; // Số tiền mua tối thiểu

    @Column(name = "max_discount")
    private Double maxDiscount; // số tiền giảm tối đa nếu type là %. có thể null

    @Column(name = "usage_limit")
    private Integer usageLimit;

    @Column(name = "user_limit")
    private Integer userLimit;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "is_active")
    private Boolean isActive;

}
