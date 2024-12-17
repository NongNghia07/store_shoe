package com.example.store.entity;

import com.example.store.util.AutoGenerateCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@AutoGenerateCode(prefix = "PRD")
@Table(name = "products", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
public class Products extends BaseEntity implements Serializable {

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "is_status", nullable = false)
    private Boolean isStatus;

    @Column(name = "image_url", nullable = true)
    private String imageURL;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_Id", nullable = false)
    @JsonIgnore
    private Categories category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @JsonIgnore
    private Set<Product_Variants> productVariants;
}
