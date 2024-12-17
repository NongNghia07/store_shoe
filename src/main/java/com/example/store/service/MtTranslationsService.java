package com.example.store.service;

import com.example.store.entity.MetadataTranslations;

import java.util.List;
import java.util.UUID;

public interface MtTranslationsService {
    MetadataTranslations getTranslation(String tableName, String columnName, UUID rowId, String userLanguageCode);
    List<MetadataTranslations> createAll(List<MetadataTranslations> metadataTranslations);
}
