package com.ll.project_13_backend.refund.repository;

import com.ll.project_13_backend.refund.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refund, Long> {
}
