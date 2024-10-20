package com.example.store.service;

import com.example.store.dto.request.ProductsRequestDTO;
import com.example.store.dto.response.ProductsResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ServiceResponseDTO<List<ProductsResponseDTO>> getAll();

    ServiceResponseDTO<PageImpl<ProductsResponseDTO>> getPage(Pageable pageable);

    ServiceResponseDTO<ProductsResponseDTO> create(ProductsRequestDTO productsRequestDTO);

    ServiceResponseDTO<ProductsResponseDTO> update(ProductsRequestDTO productsRequestDTO);

    ServiceResponseDTO<ProductsResponseDTO> findByID(UUID productID);

    ServiceResponseDTO<ProductsResponseDTO> deletes(List<UUID> ids);

    Boolean updateQuantity(UUID productID, Integer quantity, Boolean isAdd);
}
