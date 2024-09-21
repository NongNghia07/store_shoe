package com.example.store.controller;

import com.example.store.dto.request.OrderRequestDTO;
import com.example.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/order")
public class OrderRestController {
    private final OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody OrderRequestDTO orderRequest) {
        return ResponseEntity.ok(orderService.create(orderRequest, true));
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestParam List<UUID> ids, @RequestParam String toStatus) {
        return ResponseEntity.ok(orderService.updateOrderStatus(ids, toStatus));
    }
}
