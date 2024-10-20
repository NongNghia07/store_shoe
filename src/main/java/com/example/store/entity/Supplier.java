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
@Table(name = "supplier")
public class Supplier extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

}
