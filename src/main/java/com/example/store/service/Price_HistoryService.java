package com.example.store.service;

import com.example.store.dto.response.Price_HistoryResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Product_Variants;

import java.util.UUID;

public interface Price_HistoryService {
    ServiceResponseDTO<Price_HistoryResponseDTO> create(Product_Variants productVariant, Double oldPrice, Double newPrice);
}
