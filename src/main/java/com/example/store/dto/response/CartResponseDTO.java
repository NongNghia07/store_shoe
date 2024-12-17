package com.example.store.dto.response;

import com.example.store.entity.Cart;
import com.example.store.entity.Cart_Product;
import com.example.store.entity.Users;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO {
    private UUID id;
    private Double price;
    private Integer quantity;
    private Boolean isStatus = true;
    private UsersResponseDTO userDTO;
    private Set<Cart_ProductResponseDTO> cartProductDTOs;

    public CartResponseDTO(Cart cart){
        if(cart.getId()!=null)
            this.id = cart.getId();
    }
}
