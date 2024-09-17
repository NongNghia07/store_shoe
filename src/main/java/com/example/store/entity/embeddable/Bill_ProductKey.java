package com.example.store.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Bill_ProductKey implements Serializable {
    @Column(name = "variant_ID", columnDefinition = "CHAR(36)")
    private UUID variantID;

    @Column(name = "bill_ID", columnDefinition = "CHAR(36)")
    private UUID billID;
}
