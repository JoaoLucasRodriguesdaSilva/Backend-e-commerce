package com.ecommerce.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
    Long id,
    Long orderId,
    Long variantId,
    Integer quantity,
    BigDecimal unitPrice,
    String nameSnapshot,
    String skuSnapshot
) {}
