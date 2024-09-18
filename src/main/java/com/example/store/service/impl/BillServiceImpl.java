package com.example.store.service.impl;

import com.example.store.dto.request.BillRequestDTO;
import com.example.store.dto.response.BillResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Bill;
import com.example.store.repository.BillRepository;
import com.example.store.service.BillService;
import com.example.store.service.Bill_ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final ModelMapper modelMapper;
    private final Bill_ProductService billProductService;

    @Autowired
    public BillServiceImpl(BillRepository billRepository, ModelMapper modelMapper, Bill_ProductService billProductService) {
        this.billRepository = billRepository;
        this.modelMapper = modelMapper;
        this.billProductService = billProductService;
    }

    @Override
    public ServiceResponseDTO<List<BillResponseDTO>> findAll() {
        return null;
    }

    @Override
    public ServiceResponseDTO<Page<BillResponseDTO>> findAllPage(Pageable pageable) {
        return null;
    }

    @Override
    public ServiceResponseDTO<BillResponseDTO> findById(Long id) {
        return null;
    }

    @Override
    public ServiceResponseDTO<BillResponseDTO> create(BillRequestDTO billRequestDTO) {
        Bill bill = modelMapper.map(billRequestDTO, Bill.class);
        bill = billRepository.save(bill);
        billProductService.createAll(bill.getId(), billRequestDTO.getBillProductRequestDTOs());
        BillResponseDTO billResponseDTO = new BillResponseDTO(bill);
        return ServiceResponseDTO.success(HttpStatus.OK, billResponseDTO);
    }

    // note chưa làm...
    @Override
    public ServiceResponseDTO<BillResponseDTO> update(BillRequestDTO billRequestDTO) {
        Bill bill = modelMapper.map(billRequestDTO, Bill.class);
        bill = billRepository.save(bill);
        BillResponseDTO billResponseDTO = new BillResponseDTO(bill);
        return ServiceResponseDTO.success(HttpStatus.OK, billResponseDTO);
    }
}
