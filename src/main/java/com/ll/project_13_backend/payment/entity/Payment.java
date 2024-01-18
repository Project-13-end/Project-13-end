package com.ll.project_13_backend.payment.entity;

import com.ll.project_13_backend.global.BaseEntity;
import com.ll.project_13_backend.orders.entity.Orders;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

@Entity
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;

//    @Enumerated(EnumType.STRING)
//    private Status status;

    private Long price;
    @Builder.Default
    @Column(name = "is_approved")
    private Boolean isApproved = false;

}
