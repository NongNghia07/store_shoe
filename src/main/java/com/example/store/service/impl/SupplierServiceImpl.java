package com.example.store.service.impl;

import com.example.store.dto.request.SupplierRequestDTO;
import com.example.store.dto.response.SupplierResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Supplier;
import com.example.store.repository.SupplierRepository;
import com.example.store.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public ServiceResponseDTO<SupplierResponseDTO> create(SupplierRequestDTO supplierRequestDTO) {
        Supplier supplier = new Supplier();
        supplierRepository.save(supplier);
        SupplierResponseDTO supplierResponseDTO = new SupplierResponseDTO(supplier);
        return ServiceResponseDTO.success(HttpStatus.OK,"", supplierResponseDTO);
    }
}
