package com.example.store.controller;

import com.example.store.dto.request.ProductsRequestDTO;
import com.example.store.dto.response.ProductsResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/product")
public class ProductRestController {
    private final ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductsRequestDTO productsRequestDTO) {
        ServiceResponseDTO<ProductsResponseDTO> productResponseDTO = productService.create(productsRequestDTO);
        return ResponseEntity.ok(productResponseDTO);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ProductsRequestDTO productsRequestDTO) {
        ServiceResponseDTO<ProductsResponseDTO> productResponseDTO = productService.update(productsRequestDTO);
        return ResponseEntity.ok(productResponseDTO);
    }
}
