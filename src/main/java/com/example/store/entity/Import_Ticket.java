package com.example.store.entity;

import com.example.store.util.AutoGenerateCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@AutoGenerateCode(prefix = "IPT")
@Table(name = "import_ticket", uniqueConstraints = {@UniqueConstraint(columnNames = "code")})
public class Import_Ticket implements Serializable {

    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "is_status", nullable = false)
    private Boolean isStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_Id", nullable = false)
    @JsonIgnore
    private Supplier supplier;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "importTicket")
    @JsonIgnore
    private Set<ImportTicket_Product> importTicketProducts;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID(); // Gán UUID thủ công trước khi lưu
        }
    }
}
