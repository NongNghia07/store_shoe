package com.example.store.service.impl;

import com.example.store.dto.request.ImportTicketRequestDTO;
import com.example.store.dto.response.ImportTicketResponseDTO;
import com.example.store.dto.response.ImportTicket_ProductResponseDTO;
import com.example.store.dto.response.SupplierResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Import_Ticket;
import com.example.store.repository.ImportTicketRepository;
import com.example.store.service.ImportTicket_ProductService;
import com.example.store.service.Import_TicketService;
import com.example.store.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class Import_TicketServiceImpl implements Import_TicketService {
    private final ImportTicketRepository importTicketRepository;
    private final SupplierService supplierService;
    private final ModelMapper modelMapper;
    private final ImportTicket_ProductService importTicketProductService;

    @Autowired
    public Import_TicketServiceImpl(ImportTicketRepository importTicketRepository, SupplierService supplierService, ModelMapper modelMapper, ImportTicket_ProductService importTicketProductService) {
        this.importTicketRepository = importTicketRepository;
        this.supplierService = supplierService;
        this.modelMapper = modelMapper;
        this.importTicketProductService = importTicketProductService;
    }

    @Override
    @Transactional
    public ServiceResponseDTO<ImportTicketResponseDTO> create(ImportTicketRequestDTO importTicketRequestDTO) {
        if(importTicketRequestDTO.getSupplier().getId() == null){
            SupplierResponseDTO supplierDTO = supplierService.create(importTicketRequestDTO.getSupplier()).getData();
            importTicketRequestDTO.getSupplier().setId(supplierDTO.getId());
        }
        Import_Ticket importTicket = modelMapper.map(importTicketRequestDTO, Import_Ticket.class);
        Import_Ticket savedImportTicket = importTicketRepository.save(importTicket);
        Set<ImportTicket_ProductResponseDTO> importTicketProductResponseDTOs = importTicketProductService.createAll(savedImportTicket, importTicketRequestDTO.getImportTicketProducts());
        ImportTicketResponseDTO result = new ImportTicketResponseDTO(savedImportTicket);
        result.setImportTicketProductDTOs(importTicketProductResponseDTOs);
        return ServiceResponseDTO.success(HttpStatus.OK,"", result);
    }
}
