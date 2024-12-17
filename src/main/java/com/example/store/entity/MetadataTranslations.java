package com.example.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "metadata_translations")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MetadataTranslations extends BaseEntity implements Serializable {

    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column(name = "column_name", nullable = false)
    private String columnName;

    @Column(name = "row_id", columnDefinition = "CHAR(36)", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID rowId;

    @Column(name = "language_code", nullable = false, length = 10)
    private String languageCode;

    @Column(name = "translation", columnDefinition = "TEXT", nullable = false)
    private String translation;
}