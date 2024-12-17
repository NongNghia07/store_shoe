package com.example.store.dto.request;

import com.example.store.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UsersRequestDTO {
    private UUID id;
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @NotBlank
    private String name; //
    private String email; //
    @NotBlank
    private String phone; //
    @NotBlank
    private String gender; //
    private LocalDateTime dateOfBirth; //
    private Integer CCCD;
    private Double tax;
    private String level;
    private String imageURL;
    @NotBlank
    private Boolean isStatus = true;
    private Set<RoleRequestDTO> roles;
}
