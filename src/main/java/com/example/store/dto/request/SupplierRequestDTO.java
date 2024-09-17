package com.example.store.dto.request;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SupplierRequestDTO {
    private UUID id;
    private String name;
}
