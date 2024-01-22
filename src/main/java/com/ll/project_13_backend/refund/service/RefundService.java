package com.ll.project_13_backend.refund.service;

import java.math.BigDecimal;

public interface RefundService {

    // 환불 요청

    void requestRefund(Long paymentId, BigDecimal refundAmount);


}
