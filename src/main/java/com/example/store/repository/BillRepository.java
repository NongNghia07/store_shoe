package com.example.store.repository;

import com.example.store.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface BillRepository extends JpaRepository<Bill, UUID> {
    Bill findByOrder_Id(UUID id);
}
