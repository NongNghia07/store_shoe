package com.example.store.entity;

import com.example.store.enums.CustomerLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users extends BaseEntity implements Serializable {

    @Column(name = "user_name")
    private String userName;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "gender")
    private String gender;

    @Column(name = "CCCD")
    private Integer CCCD;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private CustomerLevel level;

    @Column(name = "creator")
    private Integer creator;

    @Column(name = "image")
    private String imageURL;

    @Column(name = "is_status")
    private Boolean isStatus = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private Set<Cart> carts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private Set<Order> orders;

}
