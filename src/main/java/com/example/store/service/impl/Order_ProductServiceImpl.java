package com.example.store.service.impl;

import com.example.store.dto.request.OrderRequestDTO;
import com.example.store.dto.request.Order_ProductRequestDTO;
import com.example.store.dto.response.Order_ProductResponseDTO;
import com.example.store.entity.Order;
import com.example.store.entity.Order_Product;
import com.example.store.entity.Product_Variants;
import com.example.store.entity.Products;
import com.example.store.entity.embeddable.Order_ProductKey;
import com.example.store.repository.OrderProductRepository;
import com.example.store.service.Order_ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class Order_ProductServiceImpl implements Order_ProductService {
    private final OrderProductRepository orderProductRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public Order_ProductServiceImpl(OrderProductRepository orderProductRepository, ModelMapper modelMapper) {
        this.orderProductRepository = orderProductRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<Order_ProductResponseDTO> createAll(Order order, List<Order_ProductRequestDTO> orderProductRequestDTOs) {
        List<Order_Product> orderProducts = orderProductRequestDTOs.stream().map(p -> {
            Order_Product orderProduct = modelMapper.map(p, Order_Product.class);
            Order_ProductKey id = new Order_ProductKey();
            id.setOrderID(order.getId());
            id.setVariantID(p.getProductVariant().getId());
            orderProduct.setId(id);
            orderProduct.setOrder(order);
            orderProduct.setTotalPrice(p.getQuantity() * p.getUnitPrice());
            Product_Variants productVariant = modelMapper.map(p.getProductVariant(), Product_Variants.class);
            orderProduct.setProductVariant(productVariant);
            return orderProduct;
        }).toList();
        List<Order_Product> savedOrderProducts = orderProductRepository.saveAll(orderProducts);
        Set<Order_ProductResponseDTO> result = new HashSet<>();
        savedOrderProducts.forEach(p -> result.add(new Order_ProductResponseDTO(p)));
        return result;
    }
}
