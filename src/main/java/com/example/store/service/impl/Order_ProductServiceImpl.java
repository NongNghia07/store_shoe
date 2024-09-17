package com.example.store.service.impl;

import com.example.store.dto.request.OrderRequestDTO;
import com.example.store.repository.OrderProductRepository;
import com.example.store.service.Order_ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Order_ProductServiceImpl implements Order_ProductService {
    private final OrderProductRepository orderProductRepository;

    @Autowired
    public Order_ProductServiceImpl(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public Boolean create(List<OrderRequestDTO> orderRequestDTOs) {
        return null;
    }
}
