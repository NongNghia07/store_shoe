package com.example.store.service;

import com.example.store.dto.request.SupplierRequestDTO;
import com.example.store.dto.response.SupplierResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;

public interface SupplierService {
    ServiceResponseDTO<SupplierResponseDTO> create(SupplierRequestDTO requestDTO);
}
