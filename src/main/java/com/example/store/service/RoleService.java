package com.example.store.service;

import com.example.store.dto.response.RoleResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    ServiceResponseDTO<PageImpl<RoleResponseDTO>> getPage(Pageable pageable);
}
