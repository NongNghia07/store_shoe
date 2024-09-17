package com.example.store.dto.request;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BillRequestDTO {
    private UUID id;
    private Integer quantity;
    private Double price;
    private Boolean isStatus;
    private UsersRequestDTO userRequestDTO;
    private VoucherRequestDTO voucherRequestDTO;
    private List<Bill_ProductRequestDTO> billProductRequestDTOs;
}
