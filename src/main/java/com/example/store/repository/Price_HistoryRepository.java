package com.example.store.repository;

import com.example.store.entity.Price_History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface Price_HistoryRepository extends JpaRepository<Price_History, UUID> {

}
