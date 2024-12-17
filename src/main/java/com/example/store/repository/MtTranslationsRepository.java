package com.example.store.repository;

import com.example.store.entity.MetadataTranslations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MtTranslationsRepository extends JpaRepository<MetadataTranslations, UUID> {
    Optional<MetadataTranslations> findByTableNameAndColumnNameAndRowIdAndLanguageCode(
            String tableName,
            String columnName,
            UUID rowId,
            String languageCode
    );
}
