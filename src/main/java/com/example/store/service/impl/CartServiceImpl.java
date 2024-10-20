package com.example.store.service.impl;

import com.example.store.dto.request.CartRequestDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.repository.CartRepository;
import com.example.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    @Override
    public ServiceResponseDTO<?> create(CartRequestDTO cartRequestDTO) {
        return null;
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
