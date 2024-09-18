package com.example.store.service;

import com.example.store.dto.request.ImportTicket_ProductRequestDTO;
import com.example.store.dto.response.ImportTicket_ProductResponseDTO;
import com.example.store.entity.Import_Ticket;

import java.util.List;
import java.util.Set;

public interface ImportTicket_ProductService {
    Set<ImportTicket_ProductResponseDTO> createAll(Import_Ticket import_Ticket, List<ImportTicket_ProductRequestDTO> importTicket_ProductRequestDTOs);
}
