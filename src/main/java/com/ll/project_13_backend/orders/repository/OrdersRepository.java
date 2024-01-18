package com.ll.project_13_backend.orders.repository;

import com.ll.project_13_backend.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
