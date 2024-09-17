package com.example.store.service.impl;

import com.example.store.dto.request.OrderRequestDTO;
import com.example.store.dto.response.OrderResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Order;
import com.example.store.repository.OrderRepository;
import com.example.store.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ServiceResponseDTO<OrderResponseDTO> create(OrderRequestDTO orderRequestDTO) {
        Order order = modelMapper.map(orderRequestDTO, Order.class);
        orderRepository.save(order);
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(order);
        return ServiceResponseDTO.success(HttpStatus.OK, orderResponseDTO);
    }

    @Override
    public ServiceResponseDTO<List<OrderResponseDTO>> updatePaymentStatus(List<OrderRequestDTO> orderRequestDTOs) {
        for (OrderRequestDTO orderRequestDTO : orderRequestDTOs) {
            orderRequestDTO.setIsStatus(true);
        }
        List<Order> orders = orderRequestDTOs.stream().map(orderDTO -> modelMapper.map(orderDTO, Order.class)).toList();
        orderRepository.saveAll(orders);
        List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();
        for (Order order : orders) {
            orderResponseDTOs.add(new OrderResponseDTO(order));
        }
        return ServiceResponseDTO.success(HttpStatus.OK, orderResponseDTOs);
    }
}
