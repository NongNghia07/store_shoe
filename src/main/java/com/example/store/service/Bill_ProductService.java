package com.example.store.service;

import com.example.store.dto.request.Bill_ProductRequestDTO;

import java.util.List;
import java.util.UUID;

public interface Bill_ProductService {
    Boolean createAll(UUID billID, List<Bill_ProductRequestDTO> bill_ProductRequestDTOSet);
}
