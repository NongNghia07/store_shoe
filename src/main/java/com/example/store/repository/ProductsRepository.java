package com.example.store.repository;

import com.example.store.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<Products, UUID> {
    @Query("SELECT p FROM Products p LEFT JOIN FETCH p.productVariants")
    List<Products> findAllWithVariants();
}
