package com.ll.project_13_backend.payment.repository;

import com.ll.project_13_backend.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
