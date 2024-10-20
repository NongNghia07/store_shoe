package com.example.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Products extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @Column(name = "brand")
    private String brand;

    @Column(name = "creator")
    private Integer creator;

    @Column(name = "updater")
    private Integer updater;

    @Column(name = "is_status")
    private Boolean isStatus;

    @Column(name = "image_url")
    private String imageURL;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Categories category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @JsonIgnore
    private Set<Product_Variants> productVariants;
}
