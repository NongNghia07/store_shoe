package com.example.store.service.impl;

import com.example.store.dto.response.RoleResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.MetadataTranslations;
import com.example.store.entity.Role;
import com.example.store.exception.ApiRequestException;
import com.example.store.repository.RoleRepository;
import com.example.store.service.MtTranslationsService;
import com.example.store.service.RoleService;
import com.example.store.util.LanguageContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final MtTranslationsService translationsService;

    private final String TABLE = "role";
    private final String COLUMN_DESCRIPTION = "description";

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, MtTranslationsService translationsService) {
        this.roleRepository = roleRepository;
        this.translationsService = translationsService;
    }

    @Override
    public ServiceResponseDTO<PageImpl<RoleResponseDTO>> getPage(Pageable pageable) {
        Page<Role> page = roleRepository.findAll(pageable);
        List<RoleResponseDTO> result = new ArrayList<>();
        for (Role role : page.getContent()) {
            MetadataTranslations translations = translationsService
                    .getTranslation(TABLE, COLUMN_DESCRIPTION, role.getId(), LanguageContext.getLanguage());
            result.add(new RoleResponseDTO(role, translations));
        }
        return ServiceResponseDTO.success(HttpStatus.OK,"", new PageImpl<>(result, pageable, page.getTotalElements()));
    }
}
