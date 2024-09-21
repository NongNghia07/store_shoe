package com.example.store.service;

import com.example.store.dto.request.BillRequestDTO;
import com.example.store.dto.response.BillResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Order;
import com.example.store.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BillService {

    ServiceResponseDTO<List<BillResponseDTO>> findAll();

    ServiceResponseDTO<Page<BillResponseDTO>> findAllPage(Pageable pageable);

    ServiceResponseDTO<BillResponseDTO> findById(Long id);

    Boolean create(List<Order> orders);

    Boolean updateStatus(Order order);

    ServiceResponseDTO<BillResponseDTO> update(BillRequestDTO billRequestTO);
}
