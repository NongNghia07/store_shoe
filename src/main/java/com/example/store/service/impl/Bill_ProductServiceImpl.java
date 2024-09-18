package com.example.store.service.impl;

import com.example.store.dto.request.Bill_ProductRequestDTO;
import com.example.store.entity.Bill_Product;
import com.example.store.exception.ApiRequestException;
import com.example.store.repository.BillProductRepository;
import com.example.store.service.Bill_ProductService;
import com.example.store.service.ProductService;
import com.example.store.service.Product_VariantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Bill_ProductServiceImpl implements Bill_ProductService {
    private final BillProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final Product_VariantService productVariantService;
    private final ProductService productService;

    @Autowired
    public Bill_ProductServiceImpl(BillProductRepository productRepository, ModelMapper modelMapper, Product_VariantService productVariantService, ProductService productService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.productVariantService = productVariantService;
        this.productService = productService;
    }

    @Override
    public Boolean createAll(UUID billID, List<Bill_ProductRequestDTO> bill_ProductRequestDTOSet) {
        try {
            bill_ProductRequestDTOSet.forEach(p -> p.setBillID(billID));
            Set<Bill_Product> billProducts = bill_ProductRequestDTOSet.stream().map(p -> modelMapper.map(p, Bill_Product.class)).collect(Collectors.toSet());
            productRepository.saveAll(billProducts);
            Map<String, Object> map = productVariantService.sellQuantity(bill_ProductRequestDTOSet);
            productService.updateQuantity((UUID) map.get("productID"), (Integer) map.get("totalQuantity"));
            return true;
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage());
        }
    }
}
