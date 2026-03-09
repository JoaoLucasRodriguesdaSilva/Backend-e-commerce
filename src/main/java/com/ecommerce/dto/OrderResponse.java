package com.ecommerce.dto;

import com.ecommerce.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
    Long id,
    Long customerId,
    OrderStatus status,
    BigDecimal subtotal,
    BigDecimal discount,
    BigDecimal shipping,
    BigDecimal total,
    LocalDateTime createdAt
) {}
