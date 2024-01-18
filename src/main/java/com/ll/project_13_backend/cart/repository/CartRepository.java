package com.ll.project_13_backend.cart.repository;

import com.ll.project_13_backend.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
