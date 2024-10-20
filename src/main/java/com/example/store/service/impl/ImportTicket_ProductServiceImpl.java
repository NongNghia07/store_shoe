package com.example.store.service.impl;

import com.example.store.dto.request.ImportTicket_ProductRequestDTO;
import com.example.store.dto.response.ImportTicket_ProductResponseDTO;
import com.example.store.entity.ImportTicket_Product;
import com.example.store.entity.Import_Ticket;
import com.example.store.entity.Product_Variants;
import com.example.store.entity.embeddable.ImportTicket_ProductKey;
import com.example.store.repository.ImportTicketProductRepository;
import com.example.store.service.ImportTicket_ProductService;
import com.example.store.service.ProductService;
import com.example.store.service.Product_VariantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.Lock;


@Service
public class ImportTicket_ProductServiceImpl implements ImportTicket_ProductService {
    private final ImportTicketProductRepository importTicketProductRepository;
    private final ModelMapper modelMapper;
    private final ProductService productService;
    private final Product_VariantService productVariantService;
    private final Lock lock;

    @Autowired
    public ImportTicket_ProductServiceImpl(ImportTicketProductRepository importTicketProductRepository, ModelMapper modelMapper, ProductService productService, Product_VariantService productVariantService, Lock lock) {
        this.importTicketProductRepository = importTicketProductRepository;
        this.modelMapper = modelMapper;
        this.productService = productService;
        this.productVariantService = productVariantService;
        this.lock = lock;
    }

    // Xử lý đồng bộ
    @Override
    public Set<ImportTicket_ProductResponseDTO> createAll(Import_Ticket import_Ticket, List<ImportTicket_ProductRequestDTO> importTicket_ProductRequestDTOs) {
        try {
            lock.lock();
            List<ImportTicket_Product> importTicketProducts = importTicket_ProductRequestDTOs.stream()
                    .map(p -> {
                        // Sử dụng modelMapper để ánh xạ DTO sang thực thể
                        ImportTicket_Product importTicketProduct = modelMapper.map(p, ImportTicket_Product.class);

                        // Tạo khóa phức hợp (composite key)
                        ImportTicket_ProductKey id = new ImportTicket_ProductKey();
                        id.setImportTicketID(import_Ticket.getId());
                        id.setVariantID(p.getProductVariant().getId()); // Giả sử p.getProductVariant() không null

                        importTicketProduct.setId(id);

                        // Gán các thuộc tính productVariant và importTicket cho thực thể
                        importTicketProduct.setImportTicket(import_Ticket); // Gán importTicket vào thực thể
                        Product_Variants productVariant = modelMapper.map(p.getProductVariant(), Product_Variants.class);
                        importTicketProduct.setProductVariant(productVariant); // Gán productVariant
                        return importTicketProduct;
                    })
                    .toList();
            List<ImportTicket_Product> savedImportTicketProducts = importTicketProductRepository.saveAll(importTicketProducts);
            Map<String, Object> map = productVariantService.addQuantity(savedImportTicketProducts, null);
            productService.updateQuantity((UUID) map.get("productID"), (Integer) map.get("totalQuantity"), true);
            Set<ImportTicket_ProductResponseDTO> result = new HashSet<>();
            savedImportTicketProducts.forEach(p -> result.add(new ImportTicket_ProductResponseDTO(p)));
            return result;
        } finally {
            lock.unlock();
        }
    }
}
