package com.example.store.service.impl;

import com.example.store.dto.request.Cart_ProductRequestDTO;
import com.example.store.dto.response.Cart_ProductResponseDTO;
import com.example.store.entity.Cart;
import com.example.store.entity.Cart_Product;
import com.example.store.repository.Cart_ProductRepository;
import com.example.store.service.Cart_ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class Cart_ProductServiceImpl implements Cart_ProductService {
    private final Cart_ProductRepository cart_productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public Cart_ProductServiceImpl(Cart_ProductRepository cartProductRepository, ModelMapper modelMapper) {
        cart_productRepository = cartProductRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<Cart_ProductResponseDTO> createAll(Cart cart, List<Cart_ProductRequestDTO> cart_productRequestDTOs) {
        List<Cart_Product> cart_products = cart_productRequestDTOs.stream().map(p -> modelMapper.map(p, Cart_Product.class)).toList();
        cart_products.forEach(p -> p.setCart(cart));
        List<Cart_Product> cartProducts = cart_productRepository.saveAll(cart_products);
        Set<Cart_ProductResponseDTO> cartProductResponseDTOS = new HashSet<>();
        for (Cart_Product cart_product : cartProducts) {
            cartProductResponseDTOS.add(new Cart_ProductResponseDTO(cart_product));
        }
        return cartProductResponseDTOS;
    }
}
