package com.example.store.dto.request;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoucherRequestDTO {
    private UUID id;
    private String type; // %, số tiền cố định
    private Double value;
    private Double minPurchaseAmount; // Số tiền mua tối thiểu
    private Double maxDiscount; // số tiền giảm tối đa nếu type là %. có thể null
    private Integer usageLimit; // giới hạn sử dụng
    private Integer userLimit; // giới hạn người sử dụng
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
}
