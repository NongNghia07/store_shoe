package com.example.store.repository;

import com.example.store.entity.Order_Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderProductRepository extends JpaRepository<Order_Product, UUID> {
}
