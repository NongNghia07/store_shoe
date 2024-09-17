package com.example.store.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vouchers")
public class Vouchers implements Serializable {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "code")
    private String code;

    @Column(name = "discout")
    private String discout;

    @Column(name = "type")
    private Boolean type;

    @Column(name = "is_status")
    private Boolean isStatus = false;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID(); // Gán UUID thủ công trước khi lưu
        }
    }
}
