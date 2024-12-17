package com.example.store.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Role extends BaseEntity implements GrantedAuthority, Serializable {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "is_status", nullable = false)
    private Boolean isStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_role_id")
    private Role parentRole;  // role cha nếu có

    @OneToMany(mappedBy = "parentRole")
    private Set<Role> subRoles;

    @ManyToMany(mappedBy = "roles")
    private Set<Users> users; // Mỗi vai trò có thể có nhiều người dùng

    @Override
    public String getAuthority() {
        return this.name;  // Vai trò (roleName) sẽ được coi là quyền (authority)
    }

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID(); // Gán UUID thủ công trước khi lưu
        }
    }
}
