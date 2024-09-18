package com.example.store.service;

import com.example.store.dto.request.ImportTicketRequestDTO;
import com.example.store.dto.response.ImportTicketResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;

public interface Import_TicketService {
    ServiceResponseDTO<ImportTicketResponseDTO> create(ImportTicketRequestDTO importTicketRequestDTO);
}
