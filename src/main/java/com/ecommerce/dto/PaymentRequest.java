package com.ecommerce.dto;

import com.ecommerce.enums.PaymentMethod;
import com.ecommerce.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentRequest(
    Long orderId,
    PaymentMethod method,
    PaymentStatus status,
    BigDecimal amount,
    Integer installments,
    String gateway,
    String gatewayTransactionId,
    LocalDateTime paidAt
) {}
