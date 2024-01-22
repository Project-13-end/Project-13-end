package com.ll.project_13_backend.payment.service;

import com.ll.project_13_backend.payment.entity.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {


    //결제 생성
    void createPayment(final long paymentId, BigDecimal amount , long orderId);

    // 결제 삭제
    void cancelPayment(final Long paymentId);

    // 결제 조회

    List<Payment> getPaymentHistory(final String MemberId, final Long orderId);


    // 결제 갱신
    void updatePaymentStatus(final Long paymentId);







}


