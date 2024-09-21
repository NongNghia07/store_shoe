package com.example.store.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Order_ProductKey implements Serializable {
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "variant_ID", columnDefinition = "CHAR(36)")
    private UUID variantID;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "order_ID", columnDefinition = "CHAR(36)")
    private UUID orderID;
}
