package com.example.store.dto.response;

import com.example.store.entity.MetadataTranslations;
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
    private String description;

    public RoleResponseDTO(Role role, MetadataTranslations translations) {
        this.id = role.getId();
        this.name = role.getName();
        if(translations != null) {
            this.description = translations.getTranslation();
        }
    }
}
