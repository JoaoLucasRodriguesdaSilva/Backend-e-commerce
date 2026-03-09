package com.ecommerce.dto;

import com.ecommerce.enums.OrderStatus;
import java.math.BigDecimal;

public record OrderRequest(
    Long customerId,
    OrderStatus status,
    BigDecimal subtotal,
    BigDecimal discount,
    BigDecimal shipping,
    BigDecimal total
) {}
