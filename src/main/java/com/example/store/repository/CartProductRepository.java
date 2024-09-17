package com.example.store.repository;
import com.example.store.entity.Cart_Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartProductRepository extends JpaRepository<Cart_Product, UUID> {

}
