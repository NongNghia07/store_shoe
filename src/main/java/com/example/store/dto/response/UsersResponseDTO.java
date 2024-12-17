package com.example.store.dto.response;

import com.example.store.entity.Users;
import com.example.store.enums.CustomerLevel;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsersResponseDTO {
    private UUID id;
    private String userName;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private Integer CCCD;
    private Double tax;
    private CustomerLevel level;
    private UUID creator;
    private String imageURL;
    private Boolean isStatus = true;
    private Set<RoleResponseDTO> role;
    private Set<CartResponseDTO> carts;
    private Set<OrderResponseDTO> orders;
    private Set<BillResponseDTO> bills;

    public UsersResponseDTO(Users user){
        if(user.getId() != null)
            this.id = user.getId();
        this.userName = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.dateOfBirth = user.getDateOfBirth();
        this.CCCD = user.getCCCD();
        this.tax = user.getTax();
        this.level = user.getLevel();
        this.imageURL = user.getImageURL();
        this.isStatus = user.getIsStatus();
    }
}
