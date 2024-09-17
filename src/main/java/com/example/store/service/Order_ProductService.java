package com.example.store.service;

import com.example.store.dto.request.OrderRequestDTO;
import com.example.store.entity.Order_Product;

import java.util.List;

public interface Order_ProductService {
    Boolean create(List<OrderRequestDTO> orderRequestDTOs);
}
