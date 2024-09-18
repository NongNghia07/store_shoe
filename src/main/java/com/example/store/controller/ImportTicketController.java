package com.example.store.controller;

import com.example.store.dto.request.ImportTicketRequestDTO;
import com.example.store.dto.response.ImportTicketResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.service.Import_TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/importticket")
public class ImportTicketController {
    private final Import_TicketService importTicketService;

    @Autowired
    public ImportTicketController(Import_TicketService importTicketService) {
        this.importTicketService = importTicketService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ImportTicketRequestDTO importTicketRequestDTO) {
        ServiceResponseDTO<ImportTicketResponseDTO> result = importTicketService.create(importTicketRequestDTO);
        return ResponseEntity.ok(result);
    }
}
