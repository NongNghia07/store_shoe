package com.example.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "price_history")
public class Price_History implements Serializable {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(name = "old_price", nullable = false)
    private double oldPrice;

    @Column(name = "new_price", nullable = false)
    private double newPrice;

    @Column(name = "remaining_quantity", nullable = false) //  Số lượng sản phẩm còn lại
    private Integer remainingQuantity;

    @Column(name = "change_date", nullable = false)
    private Instant changeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_Id", nullable = false)
    @JsonIgnore
    private Product_Variants productVariant;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID(); // Gán UUID thủ công trước khi lưu
        }
    }
}
