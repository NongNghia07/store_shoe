package com.example.store.service;

import com.example.store.dto.request.Order_ProductRequestDTO;
import com.example.store.dto.response.Order_ProductResponseDTO;
import com.example.store.entity.Order;

import java.util.List;
import java.util.Set;

public interface Order_ProductService {
    Set<Order_ProductResponseDTO> createAll(Order order, List<Order_ProductRequestDTO> orderProductRequestDTOs);
}
