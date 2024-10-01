package com.example.store.service;

import com.example.store.dto.request.VoucherRequestDTO;
import com.example.store.dto.response.VoucherResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;

import java.util.UUID;

public interface VoucherService {
    ServiceResponseDTO<VoucherResponseDTO> save(VoucherRequestDTO voucherRequestDTO);
    Boolean useVoucher(UUID id, Double totalAmount, Integer quantityUseVoucher);
}
