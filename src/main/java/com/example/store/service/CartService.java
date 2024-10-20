package com.example.store.service;

import com.example.store.dto.request.CartRequestDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CartService {
    ServiceResponseDTO<?> create(CartRequestDTO cartRequestDTO);
    ServiceResponseDTO<?> update(CartRequestDTO cartRequestDTO);
    ServiceResponseDTO<?> delete(List<UUID> ids);
}
