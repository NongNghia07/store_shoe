package com.example.store.enums;

public enum BillStatus {
    PAID,              // Đã thanh toán
    REFUNDED,          // Đã hoàn tiền toàn bộ
    PARTIALLY_REFUNDED,// Đã hoàn tiền một phần
    CANCELLED,         // Đã hủy
    ADJUSTED           // Đã điều chỉnh
}
