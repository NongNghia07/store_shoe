package com.example.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // Thêm EntityListeners để kích hoạt auditing
public class BaseEntity implements Serializable {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID(); // Gán UUID thủ công trước khi lưu
        }
    }
}
