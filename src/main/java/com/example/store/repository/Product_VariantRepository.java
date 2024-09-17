package com.example.store.repository;
import com.example.store.entity.Product_Variants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface Product_VariantRepository extends JpaRepository<Product_Variants, UUID> {
    List<Product_Variants> findByProduct_Id(UUID id);
}
