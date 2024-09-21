package com.example.store.dto.request;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRequestDTO {
    private UUID id;
    private Integer quantity;
    private Double price;
    private String status;
    private String deliveryAddress;
    private UsersRequestDTO user;
    private VoucherRequestDTO voucher;
    private List<Order_ProductRequestDTO> order_products;
}
