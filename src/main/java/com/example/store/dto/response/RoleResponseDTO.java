package com.example.store.dto.response;

import com.example.store.entity.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseDTO {
    private UUID id;
    private String name;

    public RoleResponseDTO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
