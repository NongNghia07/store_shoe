package com.example.store.dto.response;

import com.example.store.entity.Categories;
import com.example.store.entity.Categories;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoriesResponseDTO {
    private UUID id;
    private String name;
    private Boolean isStatus = true;

    public CategoriesResponseDTO(Categories categories) {
        if (categories.getId() != null)
            this.id = categories.getId();
        this.name = categories.getName();
        this.isStatus = categories.getIsStatus();
    }
}
