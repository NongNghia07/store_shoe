package com.example.store.service;

import com.example.store.dto.request.OrderRequestDTO;
import com.example.store.dto.response.OrderResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface OrderService {
    ServiceResponseDTO<OrderResponseDTO> create(OrderRequestDTO orderRequestDTO, Boolean isByCustomer);
    ServiceResponseDTO<Set<OrderResponseDTO>> updateOrderStatus(List<UUID> ids, String toStatus);
}
