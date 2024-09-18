package com.example.store.service;

import com.example.store.dto.request.Bill_ProductRequestDTO;
import com.example.store.dto.request.ImportTicket_ProductRequestDTO;
import com.example.store.dto.request.Product_VariantRequestDTO;
import com.example.store.dto.response.Product_VariantsResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.ImportTicket_Product;
import com.example.store.entity.Products;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface Product_VariantService {
    List<Product_VariantsResponseDTO> creates(Products products, List<Product_VariantRequestDTO> product_VariantRequestDTO);
    ServiceResponseDTO<Product_VariantsResponseDTO> update(Product_VariantRequestDTO product_VariantRequestDTO);
    Boolean delete(List<UUID> product_VariantIds);
    ServiceResponseDTO<Product_VariantsResponseDTO> findById(UUID productID);
    ServiceResponseDTO<List<Product_VariantsResponseDTO>> findAll();
    ServiceResponseDTO<List<Product_VariantsResponseDTO>> findAllByProduct_Id(UUID productID);
    List<Product_VariantsResponseDTO> updatePriceProductBase(UUID productID, double changePrice);
    Map<String, Object> addQuantity(List<ImportTicket_Product> importTicketProducts);
    Map<String, Object> sellQuantity(List<Bill_ProductRequestDTO> bill_ProductRequestDTOs);
}
