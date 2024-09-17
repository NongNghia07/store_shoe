package com.example.store.service.impl;

import com.example.store.dto.request.Bill_ProductRequestDTO;
import com.example.store.entity.Bill_Product;
import com.example.store.exception.ApiRequestException;
import com.example.store.repository.BillProductRepository;
import com.example.store.service.Bill_ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class Bill_ProductServiceImpl implements Bill_ProductService {
    private final BillProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public Bill_ProductServiceImpl(BillProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean createAll(UUID billID, List<Bill_ProductRequestDTO> bill_ProductRequestDTOSet) {
        try {
            bill_ProductRequestDTOSet.forEach(p -> {
                p.setBillID(billID);
            });
            Set<Bill_Product> billProducts = bill_ProductRequestDTOSet.stream().map(p -> modelMapper.map(p, Bill_Product.class)).collect(Collectors.toSet());
            productRepository.saveAll(billProducts);
            return true;
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage());
        }
    }
}
