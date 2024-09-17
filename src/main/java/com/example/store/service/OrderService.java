package com.example.store.service;

import com.example.store.dto.request.OrderRequestDTO;
import com.example.store.dto.response.OrderResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;

import java.util.List;

public interface OrderService {
    ServiceResponseDTO<OrderResponseDTO> create(OrderRequestDTO orderRequestDTO);
    ServiceResponseDTO<List<OrderResponseDTO>> updatePaymentStatus(List<OrderRequestDTO> orderRequestDTOs);
}
