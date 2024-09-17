package com.example.store.service.impl;

import com.example.store.dto.response.Price_HistoryResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Price_History;
import com.example.store.entity.Product_Variants;
import com.example.store.exception.ApiRequestException;
import com.example.store.repository.Price_HistoryRepository;
import com.example.store.service.Price_HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class Price_HistoryServiceImpl implements Price_HistoryService {
    private final Price_HistoryRepository price_HistoryRepository;

    @Autowired
    public Price_HistoryServiceImpl(Price_HistoryRepository priceHistoryRepository) {
        price_HistoryRepository = priceHistoryRepository;
    }

    @Override
    public ServiceResponseDTO<Price_HistoryResponseDTO> create(Product_Variants productVariant, Double oldPrice, Double newPrice) {
        try {
            Price_History price_History = new Price_History();
            price_History.setNewPrice(newPrice);
            price_History.setOldPrice(oldPrice);
            price_History.setChangeDate(LocalDateTime.now());
            price_History.setProductVariant(productVariant);
            price_HistoryRepository.save(price_History);
            Price_HistoryResponseDTO responseDTO = new Price_HistoryResponseDTO(price_History);
            return ServiceResponseDTO.success(HttpStatus.OK, responseDTO);
        }catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }
}
