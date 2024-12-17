package com.example.store.service.impl;

import com.example.store.dto.request.CategoriesRequestDTO;
import com.example.store.dto.response.CategoriesResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Categories;
import com.example.store.repository.CategoriesRepository;
import com.example.store.service.CategoriesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoriesRepository categoriesRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoriesServiceImpl(CategoriesRepository categoriesRepository, ModelMapper modelMapper) {
        this.categoriesRepository = categoriesRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ServiceResponseDTO<List<CategoriesResponseDTO>> findAll() {
        List<Categories> categories = categoriesRepository.findAll();
        List<CategoriesResponseDTO> categoriesResponseDTOS = new ArrayList<>();
        for (Categories c : categories) {
            categoriesResponseDTOS.add(new CategoriesResponseDTO(c));
        }
        return ServiceResponseDTO.success(HttpStatus.OK,"", categoriesResponseDTOS);
    }

    @Override
    public ServiceResponseDTO<List<CategoriesResponseDTO>> searchAllByName(String keyword) {
        return null;
    }

    @Override
    public ServiceResponseDTO<CategoriesResponseDTO> create(CategoriesRequestDTO categoriesRequestDTO) {
        return null;
    }

    @Override
    public ServiceResponseDTO<CategoriesResponseDTO> update(CategoriesRequestDTO categoriesRequestDTO) {
        return null;
    }

    @Override
    public ServiceResponseDTO<CategoriesResponseDTO> findById(Long id) {
        return null;
    }

    @Override
    public void setStatusFalse(Long id) {

    }
}
