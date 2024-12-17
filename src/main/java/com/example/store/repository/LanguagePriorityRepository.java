package com.example.store.repository;

import com.example.store.entity.LanguagePriority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LanguagePriorityRepository extends JpaRepository<LanguagePriority, UUID> {
}
