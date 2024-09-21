package com.example.store.dto.response;

import com.example.store.entity.*;
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
public class BillResponseDTO {
    private UUID billID;
    private LocalDateTime billDate;
    private Double totalAmount; // tổng số tiền của hóa đơn (sau khi tính thuế, giảm giá nếu có).
    private Double texAmount; // số tiền thuế
    private Double discount;
    private Double netAmount; // tổng số tiền phải thanh toán  (TotalAmount - Discount + Tax).
    private String paymentStatus; // paid, unPaid
    private Set<Order_ProductResponseDTO> orderProductResponseDTOs;

    public BillResponseDTO(Bill bill) {
    }
}
