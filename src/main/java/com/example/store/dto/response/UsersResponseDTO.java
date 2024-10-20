package com.example.store.dto.response;

import com.example.store.entity.Bill;
import com.example.store.entity.Order;
import com.example.store.entity.Users;
import com.example.store.enums.CustomerLevel;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
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
    private LocalDateTime dateOfBirth;
    private Integer CCCD;
    private Double tax;
    private CustomerLevel level;
    private Integer creator;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String imageURL;
    private Boolean isStatus = true;
    private RoleResponseDTO role;
    private Set<CartResponseDTO> carts;
    private Set<OrderResponseDTO> orders;
    private Set<BillResponseDTO> bills;

    public UsersResponseDTO(Users user){
        if(user.getId() != null)
            this.id = user.getId();
        this.userName = user.getUserName();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.dateOfBirth = user.getDateOfBirth();
        this.CCCD = user.getCCCD();
        this.tax = user.getTax();
        this.level = user.getLevel();
        this.creator = user.getCreator();
        this.createAt = user.getCreateAt();
        this.updateAt = user.getUpdateAt();
        this.imageURL = user.getImageURL();
        this.isStatus = user.getIsStatus();

        this.role = new RoleResponseDTO(user.getRole());
        if(!user.getOrders().isEmpty())
            this.orders = convertOrders(user.getOrders());
    }

    private Set<OrderResponseDTO> convertOrders(Set<Order> orders){
        Set<OrderResponseDTO> orderDTOs = new HashSet<>();
        for(Order order : orders){
            OrderResponseDTO orderDTO = new OrderResponseDTO(order);
            orderDTOs.add(orderDTO);
        }
        return orderDTOs;
    }

    private Set<BillResponseDTO> convertBills(Set<Bill> bills){
        Set<BillResponseDTO> billDTOs = new HashSet<>();
        for(Bill bill : bills){
            BillResponseDTO billDTO = new BillResponseDTO(bill);
            billDTOs.add(billDTO);
        }
        return billDTOs;
    }
}
