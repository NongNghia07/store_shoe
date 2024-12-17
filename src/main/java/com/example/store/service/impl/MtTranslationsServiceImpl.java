package com.example.store.service.impl;

import com.example.store.entity.LanguagePriority;
import com.example.store.entity.MetadataTranslations;
import com.example.store.repository.LanguagePriorityRepository;
import com.example.store.repository.MtTranslationsRepository;
import com.example.store.service.MtTranslationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MtTranslationsServiceImpl implements MtTranslationsService {
    private final MtTranslationsRepository mtTranslationsRepository;
    private final LanguagePriorityRepository languagePriorityRepository;

    @Autowired
    public MtTranslationsServiceImpl(MtTranslationsRepository mtTranslationsRepository, LanguagePriorityRepository languagePriorityRepository) {
        this.mtTranslationsRepository = mtTranslationsRepository;
        this.languagePriorityRepository = languagePriorityRepository;
    }

    @Override
    public MetadataTranslations getTranslation(String tableName, String columnName, UUID rowId, String userLanguageCode) {
        // 1. Try to find translation by user's preferred language
        Optional<MetadataTranslations> translationOpt = mtTranslationsRepository
                .findByTableNameAndColumnNameAndRowIdAndLanguageCode(tableName, columnName, rowId, userLanguageCode);

        if (translationOpt.isPresent()) {
            return translationOpt.get();
        }

        // 2. If no translation for user's language, get language priority order from the LanguagePriority table
        List<String> languagePriorityList = getLanguagePriorityList();

        // 3. Try to find translation by the next language in priority
        for (String languageCode : languagePriorityList) {
            Optional<MetadataTranslations> fallbackTranslationOpt = mtTranslationsRepository
                    .findByTableNameAndColumnNameAndRowIdAndLanguageCode(tableName, columnName, rowId, languageCode);

            if (fallbackTranslationOpt.isPresent()) {
                return fallbackTranslationOpt.get();
            }
        }

        // 4. If no translation is found, return null
        return null;
    }

    @Override
    public List<MetadataTranslations> createAll(List<MetadataTranslations> metadataTranslations) {
        return mtTranslationsRepository.saveAll(metadataTranslations);
    }

    private List<String> getLanguagePriorityList() {
        // Fetch the language priority order from your database
        List<LanguagePriority> priorities = languagePriorityRepository.findAll();
        return priorities.stream()
                .map(LanguagePriority::getPriorityOrder)
                .collect(Collectors.toList());
    }
}
