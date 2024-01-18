package com.ll.project_13_backend.refund.entity;

import com.ll.project_13_backend.global.BaseEntity;
import com.ll.project_13_backend.ordersdetail.entity.OrdersDetail;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Refund extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orders_detail_id")
    private OrdersDetail ordersDetail;

    private Long price;

    private String refundReason;
}
