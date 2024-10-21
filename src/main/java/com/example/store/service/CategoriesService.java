package com.example.store.service;

import com.example.store.dto.request.CategoriesRequestDTO;
import com.example.store.dto.response.CategoriesResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;

import java.util.List;

public interface CategoriesService {

    ServiceResponseDTO<List<CategoriesResponseDTO>> findAll();

    ServiceResponseDTO<List<CategoriesResponseDTO>> searchAllByName(String keyword);

    ServiceResponseDTO<CategoriesResponseDTO> create(CategoriesRequestDTO categoriesRequestDTO);

    ServiceResponseDTO<CategoriesResponseDTO> update(CategoriesRequestDTO categoriesRequestDTO);

    ServiceResponseDTO<CategoriesResponseDTO> findById(Long id);

    void setStatusFalse(Long id);
}
