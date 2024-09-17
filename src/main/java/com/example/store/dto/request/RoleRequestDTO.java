package com.example.store.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequestDTO {
    private UUID id;
    private String name;
}
