package com.example.store.service;

import com.example.store.dto.request.Cart_ProductRequestDTO;
import com.example.store.dto.response.Cart_ProductResponseDTO;
import com.example.store.entity.Cart;

import java.util.List;
import java.util.Set;

public interface Cart_ProductService {
    Set<Cart_ProductResponseDTO> createAll(Cart cart, List<Cart_ProductRequestDTO> cart_productRequestDTOList);
}
