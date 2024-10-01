package com.example.store.dto.request;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDTO {
    private UUID id;
    private UsersRequestDTO users;
    private Double price;
    private Integer quantity;
    private Boolean isStatus;
    private List<Cart_ProductRequestDTO> cartProducts;
}
