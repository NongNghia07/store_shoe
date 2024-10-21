package com.example.store.service.impl;

import com.example.store.dto.request.CartRequestDTO;
import com.example.store.dto.response.CartResponseDTO;
import com.example.store.dto.response.Cart_ProductResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Cart;
import com.example.store.repository.CartRepository;
import com.example.store.service.CartService;
import com.example.store.service.Cart_ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final Cart_ProductService cart_productService;
    private final ModelMapper modelMapper;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, Cart_ProductService cartProductService, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        cart_productService = cartProductService;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional
    public ServiceResponseDTO<?> create(CartRequestDTO cartRequestDTO) {
        Cart cart = modelMapper.map(cartRequestDTO, Cart.class);
        Cart savedCart = cartRepository.save(cart);
        Set<Cart_ProductResponseDTO> savedCartProducts = cart_productService.createAll(savedCart, cartRequestDTO.getCartProducts());
        CartResponseDTO result = new CartResponseDTO(savedCart);
        result.setCartProductDTOs(savedCartProducts);
        return ServiceResponseDTO.success(HttpStatus.OK, result);
    }

    @Override
    public ServiceResponseDTO<?> update(CartRequestDTO cartRequestDTO) {
        return null;
    }

    @Override
    public ServiceResponseDTO<?> delete(List<UUID> ids) {
        return null;
    }
}
