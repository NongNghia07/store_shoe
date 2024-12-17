package com.example.store.entity;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "supplier", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Supplier extends BaseEntity implements Serializable {

    @Column(name = "is_status", nullable = false)
    private Boolean isStatus;
}
