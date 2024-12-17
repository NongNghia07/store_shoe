package com.example.store.controller;

import com.example.store.dto.request.ProductsRequestDTO;
import com.example.store.dto.response.ProductsResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/")
public class ProductRestController {
    private final ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        ServiceResponseDTO<List<ProductsResponseDTO>> productResponseDTOs = productService.getAll();
        return ResponseEntity.ok(productResponseDTOs);
    }

    @PostMapping("/product/importExcel")
    public ServiceResponseDTO<?> createToExcel(@RequestParam("file") MultipartFile file) throws Exception{
        if (file.isEmpty()) {
            return null;
        }
            // Lấy InputStream từ MultipartFile
            InputStream is = file.getInputStream();

            // Gọi service để xử lý dữ liệu
            return productService.createToExcel(is);
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
