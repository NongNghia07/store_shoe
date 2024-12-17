package com.example.store.dto.response;

import com.example.store.entity.*;
import com.example.store.enums.OrderStatus;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderResponseDTO {
    private UUID id;
    private Double totalAmount;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private OrderStatus status;
    private String paymentMethod;
    private String deliveryAddress;
    private UsersResponseDTO userDTO;
    private VoucherResponseDTO voucherDTO;
    private Set<Order_ProductResponseDTO> orderProductDTOs;

    public OrderResponseDTO(Order order) {
        if(order.getId() != null)
            this.id = order.getId();
        this.totalAmount = order.getTotalAmount();
        this.status = order.getStatus();
        if(order.getVoucher() != null)
            this.voucherDTO = new VoucherResponseDTO(order.getVoucher());
    }

}
