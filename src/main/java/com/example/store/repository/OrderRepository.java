package com.example.store.repository;

import com.example.store.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.order_Products WHERE o.id=?1")
    Order findOrderById(UUID id);

    @Query(nativeQuery = true, value = "SELECT SUM(user_id) FORM Order WHERE user_id = ?1 and voucher_id = ?2 " +
            "GROUP BY user_id")
    Integer quantityUserUseVoucher(UUID userID, UUID voucherID);
}
