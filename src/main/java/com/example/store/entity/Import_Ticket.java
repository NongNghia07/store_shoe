package com.example.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "import_ticket")
public class Import_Ticket implements Serializable {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(name = "code")
    private String code;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "creator")
    private Integer creator;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "is_status")
    private Boolean isStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_ID")
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
