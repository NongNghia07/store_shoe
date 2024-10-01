package com.example.store.service;

import com.example.store.dto.request.Cart_ProductRequestDTO;
import com.example.store.entity.Cart;

import java.util.List;

public interface Cart_ProductService {
    Boolean createAll(Cart cart, List<Cart_ProductRequestDTO> cart_productRequestDTOList);
}
